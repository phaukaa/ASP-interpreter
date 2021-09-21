package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt {
    
    AspName name;
    AspExpr expr;
    AspSuite suite;
    
    AspForStmt(int n) {
        super(n);
    }

    static AspForStmt parse(Scanner s) {
        enterParser("for stmt");
        AspForStmt afs = new AspForStmt(s.curLineNum());
        skip(s, forToken);
        afs.name = AspName.parse(s);
        skip(s, inToken);
        afs.expr = AspExpr.parse(s);
        skip(s, colonToken);
        afs.suite = AspSuite.parse(s);
        leaveParser("for stmt");
        return afs;
    }

    @Override
    void prettyPrint() {
        prettyWrite("for ");
        name.prettyPrint();
        prettyWrite(" in ");
        expr.prettyPrint();
        prettyWrite(" : ");
        suite.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue list = expr.eval(curScope);
        long size = list.evalLen(this).getIntValue("for loop", this);
        for(long i = 0; i < size; i++) {
            RuntimeValue value = list.evalSubscription(new RuntimeIntValue(i), this);
            curScope.assign(name.toString(), value);
            suite.eval(curScope);
            trace("for #" + (i+1) + " : " + name.toString() + " = " + value.showInfo());
        }
        return null;
    }
}