package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExprStmt extends AspSmallStmt {

    AspExpr ae;

    AspExprStmt(int n) {
        super(n);
    }

    static AspExprStmt parse(Scanner s) {
        enterParser(" expression Statement");
        AspExprStmt aes = new AspExprStmt(s.curLineNum());
        aes.ae = AspExpr.parse(s);
        leaveParser(" expression Statement");
        return aes;
    }

    @Override
    void prettyPrint() {
        ae.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return ae.eval(curScope);
    }
}