package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorPrefix extends AspSyntax {

    TokenKind opr;

    AspFactorPrefix(int n) {
        super(n);
    }

    TokenKind getPrefix() {
        return opr;
    }

    static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
        afp.opr = s.curToken().kind;
        if(s.curToken().kind == plusToken || s.curToken().kind == minusToken) {
            skip(s, s.curToken().kind);
        }
        leaveParser("factor prefix");
        return afp;
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