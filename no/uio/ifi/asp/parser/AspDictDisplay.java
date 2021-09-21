package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom {
    
    ArrayList<AspStringLiteral> string = new ArrayList<>();
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s) {
        enterParser("dict display");
        AspDictDisplay adp = new AspDictDisplay(s.curLineNum());
        skip(s, leftBraceToken);
        while (s.curToken().kind != rightBraceToken) {
            adp.string.add(AspStringLiteral.parse(s));
            skip(s, colonToken);
            adp.expr.add(AspExpr.parse(s)); 
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightBraceToken);
        leaveParser("dict display");
        return adp;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" {");
        int i = 0;
        for (i = 0; i < string.size() -1; i++) {
            string.get(i).prettyPrint();
            prettyWrite(" : ");
            expr.get(i).prettyPrint();
            prettyWrite(" ,");
        }
        string.get(i).prettyPrint();
        prettyWrite(" : ");
        expr.get(i).prettyPrint();
        prettyWrite("} ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        HashMap<String, RuntimeValue> result = new HashMap<>();
        for (int i = 0; i < string.size(); i++) {
            result.put(string.get(i).getString(), expr.get(i).eval(curScope));
        }
        return new RuntimeDictValue(result);
    }
}