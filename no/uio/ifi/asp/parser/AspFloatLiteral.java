package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFloatLiteral extends AspAtom {

    double number;

    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");
        AspFloatLiteral ail = new AspFloatLiteral(s.curLineNum());
        ail.number = s.curToken().floatLit;
        skip(s, floatToken);
        leaveParser("float literal");
        return ail;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" " + number + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeFloatValue(number);
    }
}