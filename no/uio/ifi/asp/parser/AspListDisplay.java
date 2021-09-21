package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s) {
        AspListDisplay ald = new AspListDisplay(s.curLineNum()) ;
        enterParser("list display");
        skip(s, leftBracketToken);
        while (s.curToken().kind != rightBracketToken) {
            ald.expr.add(AspExpr.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightBracketToken);
        leaveParser("list display");
        return ald;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" [");
        int i = 0;
        for (i = 0; i < expr.size() - 1; i++) {
            expr.get(i).prettyPrint();
            prettyWrite(" ,");
        }
        if (expr.size() > 0) expr.get(i).prettyPrint();
        prettyWrite("] ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> value = new ArrayList<>();
        for (AspExpr e: expr) {
            value.add(e.eval(curScope));
        }
        return new RuntimeListValue(value);
    }
}