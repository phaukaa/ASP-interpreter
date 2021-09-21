package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNoneLiteral extends AspAtom {
    
    boolean none = false;

    AspNoneLiteral(int n) {
        super(n);
    }

    static AspNoneLiteral parse(Scanner s) {
        enterParser("none literal");
        AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
        anl.none = true;
        skip(s, noneToken);
        leaveParser("none literal");
        return anl;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" None ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeNoneValue();
    }
}