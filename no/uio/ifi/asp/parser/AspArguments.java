package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix {
    
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }

    static AspArguments parse(Scanner s) {
        enterParser("arguments");
        AspArguments aa = new AspArguments(s.curLineNum());
        skip(s, leftParToken);
        while (s.curToken().kind != rightParToken) {
            aa.expr.add(AspExpr.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightParToken);
        leaveParser("arguments");
        return aa;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" (");
        int i = 0;
        for (i = 0; i < expr.size(); i++) {
            expr.get(i).prettyPrint();
            if (i < expr.size() - 1) prettyWrite(" ,");
        }
        prettyWrite(") ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> value = new ArrayList<>();
        for(AspExpr n: expr) {
            value.add(n.eval(curScope));
        }
        return new RuntimeListValue(value);
    }
}