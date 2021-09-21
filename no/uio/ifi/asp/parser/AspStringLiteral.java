package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspStringLiteral extends AspAtom {
    
    String string;

    AspStringLiteral(int n){
        super(n);
    }

    public String getString() {
        return string;
    }

    static AspStringLiteral parse(Scanner s) {
        enterParser("string literal");
        AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
        asl.string = s.curToken().stringLit;
        skip(s, stringToken);
        leaveParser("string literal");
        return asl;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" \"" + string + "\" ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeStringValue(string);
    }
}