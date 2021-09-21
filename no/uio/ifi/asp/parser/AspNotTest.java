package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNotTest extends AspSyntax {

    boolean not = false;
    AspComparison comp;

    AspNotTest(int n) {
        super(n);
    }

    static AspNotTest parse(Scanner s) {
        enterParser("not test");
        AspNotTest ant = new AspNotTest(s.curLineNum());
        if (s.curToken().kind == notToken) {
            ant.not = true;
            skip(s, notToken);
        }
        ant.comp = AspComparison.parse(s);
        leaveParser("not test");
        return ant;
    }

    @Override
    void prettyPrint() {
        if (not) {
            prettyWrite(" not ");
        }
        comp.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = comp.eval(curScope);
        if(not) {
            value = value.evalNot(this);
        }
        return value;
    }
}