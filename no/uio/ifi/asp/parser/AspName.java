package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspName extends AspAtom {
    
    String name;
    private Token nameToken;

    AspName(int n) {
        super(n);
    }

    static AspName parse(Scanner s) {
        enterParser("name");
        AspName an = new AspName(s.curLineNum());
        an.nameToken = s.curToken();
        test(s, TokenKind.nameToken);
        s.readNextToken();
        leaveParser("name");
        return an;
    }

    @Override
    void prettyPrint() {
        prettyWrite(nameToken.name);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return curScope.find(nameToken.name, this);
    }


    @Override 
    public String toString() {
        return nameToken.name;
    }
}