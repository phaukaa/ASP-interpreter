package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspReturnStmt extends AspSmallStmt {

    AspExpr expr;
    
    AspReturnStmt(int n) {
        super(n);
    }

    static AspReturnStmt parse(Scanner s) {
        enterParser("return");
        AspReturnStmt ars = new AspReturnStmt(s.curLineNum());
        skip(s, returnToken);
        ars.expr = AspExpr.parse(s);
        leaveParser("return");
        return ars;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" return ");
        expr.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = expr.eval(curScope);
        trace("return " + value.showInfo());
        throw new RuntimeReturnValue(value, this.lineNum);
    }
}