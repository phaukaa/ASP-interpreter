package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTermOpr extends AspSyntax {

    TokenKind term;

    AspTermOpr(int n) {
        super(n);
    }

    static AspTermOpr parse(Scanner s) {
        enterParser("term opr");
        AspTermOpr ato = new AspTermOpr(s.curLineNum());
        ato.term = s.curToken().kind;
        skip(s, s.curToken().kind);
        leaveParser("term opr");
        return ato;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" " + term.toString() + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}