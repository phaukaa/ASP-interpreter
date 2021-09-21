package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIfStmt extends AspCompoundStmt {
    
    ArrayList<AspExpr> expr = new ArrayList<>();
    ArrayList<AspSuite> ifSuite = new ArrayList<>();
    AspSuite elseSuite;
    boolean aspelse = false;
    
    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s) {
        enterParser("if stmt");
        AspIfStmt ais = new AspIfStmt(s.curLineNum());
        skip(s, ifToken);
        while (true) {
            ais.expr.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.ifSuite.add(AspSuite.parse(s));
            if (s.curToken().kind != elifToken) break;
            skip(s, elifToken);
        }
        if (s.curToken().kind == elseToken) {
            skip(s, elseToken);
            skip(s, colonToken);
            ais.elseSuite = AspSuite.parse(s);
            ais.aspelse = true;
        }
        leaveParser("if stmt");
        return ais;
    }

    @Override
    void prettyPrint() {
        int numElif = 0;
        prettyWrite("if ");
        for (AspExpr e: expr) {
            if (numElif > 0) prettyWrite("elif ");
            e.prettyPrint();
            prettyWrite(" : ");
            ifSuite.get(numElif).prettyPrint();
            ++numElif;
        }
        if (aspelse) {
            prettyWrite("else");
            prettyWrite(" : ");
            elseSuite.prettyPrint();
        } 
    }

    @Override
    RuntimeValue eval(RuntimeScope scope) throws RuntimeReturnValue {
        for (int i = 0; i < ifSuite.size(); i++) {
            RuntimeValue cond = expr.get(i).eval(scope);
            if (cond.getBoolValue("if statement", this)) {
                trace("if True alt #" + i+1 + ": ...");
                return ifSuite.get(i).eval(scope);
            }
        }
        if (aspelse) {
            trace("else: ...");
            return elseSuite.eval(scope);
        }
        return new RuntimeNoneValue();
    }
}