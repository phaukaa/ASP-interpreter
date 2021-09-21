package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspComparison extends AspSyntax {
    
    ArrayList<AspCompOpr> compOpr = new ArrayList<>();
    ArrayList<AspTerm> term = new ArrayList<>();
    
    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());
        while (true) {
            ac.term.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;
            ac.compOpr.add(AspCompOpr.parse(s));
        }
        leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        int i = 0;
        int nComp = 0;
        for (i = 0; i < term.size(); i++) {
            if (nComp > 0) compOpr.get(i - 1).prettyPrint();
            term.get(i).prettyPrint();
            ++nComp;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = term.get(0).eval(curScope);
        if (term.size() == 1) return v;
        RuntimeValue result = null;
        for (int i = 1; i < term.size(); i++) {
            TokenKind k = compOpr.get(i-1).opr;
            RuntimeValue other = term.get(i).eval(curScope);
            switch(k) {
                case greaterToken:
                    result = v.evalGreater(other, this); break;
                case lessToken:
                    result = v.evalLess(other, this); break;
                case doubleEqualToken:
                    result = v.evalEqual(other, this); break;
                case greaterEqualToken:
                    result = v.evalGreaterEqual(other, this); break;
                case lessEqualToken:
                    result = v.evalLessEqual(other, this); break;
                case notEqualToken:
                    result = v.evalNotEqual(other, this); break;
                default:
                    Main.panic("Illegal comparison operator: " + k + "!");
            }
            if (!result.getBoolValue("comparison", this)) {
                return new RuntimeBoolValue(false);
            }
            v = other;
        }
        if (result == null) {
            Main.panic("Result of comparison is null!");
            return null;
        }
        return new RuntimeBoolValue(result.getBoolValue("comparison", this));
    }
}