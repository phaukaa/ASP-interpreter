package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspBooleanLiteral extends AspAtom {
    
    boolean token;

    AspBooleanLiteral(int n) {
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
        switch(s.curToken().kind) {
            case trueToken:
                abl.token = true;
                skip(s, trueToken);
                break;
            case falseToken:
                abl.token = false;
                skip(s, falseToken);
                break;
            default:
                parserError("Expected an expression boolean but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("boolean literal");
        return abl;
    }

    @Override
    void prettyPrint() {
        if (token) prettyWrite(" True ");
        else {prettyWrite(" False ");}
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeBoolValue(token);
    }
}