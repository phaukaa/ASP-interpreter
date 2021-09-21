package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactor extends AspSyntax {
    
    ArrayList<AspFactorOpr> factorOpr = new ArrayList<>();
    ArrayList<AspFactorPrefix> factorPrefix = new ArrayList<>();
    ArrayList<AspPrimary> primary = new ArrayList<>();

    AspFactor(int n) {
        super(n);
    }

    static AspFactor parse(Scanner s) {
        enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());
        while (true) {
            if (s.isFactorPrefix()) {
                af.factorPrefix.add(AspFactorPrefix.parse(s));
            }
            else {af.factorPrefix.add(null);}
            af.primary.add(AspPrimary.parse(s));
            if (!s.isFactorOpr()) break;
            af.factorOpr.add(AspFactorOpr.parse(s));
        }
        leaveParser("factor");
        return af;

    }

    @Override
    void prettyPrint() {
        int i = 0;
        int nFac = 0;
        for (i = 0; i < primary.size(); i++) {
            if (nFac > 0) factorOpr.get(i - 1).prettyPrint();
            if (i < factorPrefix.size() && factorPrefix.get(i) != null) {
                factorPrefix.get(i).prettyPrint();
            }
            primary.get(i).prettyPrint();
            ++nFac;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = primary.get(0).eval(curScope);
        AspFactorPrefix prefix = factorPrefix.get(0);
        TokenKind tk = null;
        if (prefix != null) {
            tk = prefix.getPrefix();
            if(tk == minusToken) {
                v = v.evalNegate(this);
            } else if (tk == plusToken) {
                v = v.evalPositive(this);
            }
        }
        
        for (int i = 1; i < primary.size(); i++) {

            
            RuntimeValue other = primary.get(i).eval(curScope);
            prefix = factorPrefix.get(i);
            if (prefix != null) {
                tk = prefix.getPrefix();
                if(tk == minusToken) {
                    other = other.evalNegate(this);
                } else if (tk == plusToken) {
                    other = other.evalPositive(this);
                }
            }
            
            TokenKind k = factorOpr.get(i-1).getOpr();
            switch(k) {
                case astToken:
                    v = v.evalMultiply(other, this); break;
                case slashToken:
                    v = v.evalDivide(other, this); break;
                case percentToken:
                    v = v.evalModulo(other, this); break;
                case doubleSlashToken:
                    v = v.evalIntDivide(other, this); break;
                default:
                    Main.panic("Illegal factor operator: " + k + "!");
            }
        }
        return v;
    }
}