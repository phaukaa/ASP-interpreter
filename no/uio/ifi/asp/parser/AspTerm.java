package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTerm extends AspSyntax {
    
    ArrayList<AspTermOpr> termOpr = new ArrayList<>();
    ArrayList<AspFactor> factor = new ArrayList<>();
    
    AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {
        enterParser("term");
        AspTerm at = new AspTerm(s.curLineNum());
        while (true) {
            at.factor.add(AspFactor.parse(s));
            if (!s.isTermOpr()) break;
            at.termOpr.add(AspTermOpr.parse(s));
        }
        leaveParser("term");
        return at;
    }

    @Override
    void prettyPrint() {
        int i = 0;
        int nTerm = 0;
        for (i = 0; i < factor.size(); i++) {
            if (nTerm > 0) termOpr.get(i - 1).prettyPrint();
            factor.get(i).prettyPrint();
            ++nTerm;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = factor.get(0).eval(curScope);
        for (int i = 1; i < factor.size(); i++) {
            TokenKind k = termOpr.get(i-1).term;
            switch(k) {
                case minusToken:
                    v = v.evalSubtract(factor.get(i).eval(curScope), this); break;
                case plusToken:
                    v = v.evalAdd(factor.get(i).eval(curScope), this); break;
                default:
                    Main.panic("Illegal term operator: " + k + "!");
            }
        }
        return v;
    }
}