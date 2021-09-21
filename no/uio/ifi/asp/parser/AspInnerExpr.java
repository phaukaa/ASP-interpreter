package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInnerExpr extends AspAtom {

    AspExpr ae = null;

    AspInnerExpr(int n) {
        super(n);
    }

    static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
        skip(s, leftParToken);
        aie.ae = AspExpr.parse(s);
        skip(s, rightParToken);
        leaveParser("inner expr");
        return aie;
    }

    @Override
    void prettyPrint() {
        prettyWrite("(");
        ae.prettyPrint();
        prettyWrite(")");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return ae.eval(curScope);
    }
}