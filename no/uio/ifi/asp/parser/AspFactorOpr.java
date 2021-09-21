package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorOpr extends AspSyntax {

    TokenKind opr;

    AspFactorOpr(int n) {
        super(n);
    }

    TokenKind getOpr() {
        return opr;
    }

    static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");
        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());
        afo.opr = s.curToken().kind;
        skip(s, afo.opr);
        leaveParser("factor opr");
        return afo;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" " + opr.toString() + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;   
    }
}