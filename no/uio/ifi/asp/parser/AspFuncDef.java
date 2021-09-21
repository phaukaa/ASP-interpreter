package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCompoundStmt {
    
    AspName outerName;
    ArrayList<AspName> innerNames = new ArrayList<>();
    AspSuite suite;

    AspFuncDef(int n) {
        super(n);
    }

    static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef afd = new AspFuncDef(s.curLineNum());
        skip(s, defToken);
        afd.outerName = AspName.parse(s);
        skip(s, leftParToken);
        while (s.curToken().kind != rightParToken) {
            afd.innerNames.add(AspName.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        afd.suite = AspSuite.parse(s);
        leaveParser("func def");
        return afd;
    }

    @Override
    void prettyPrint() {
        prettyWrite("def ");
        outerName.prettyPrint();
        prettyWrite("(");
        int nCommas = 0;
        for (AspName name: innerNames) {
            if (nCommas > 0) prettyWrite(", ");
            name.prettyPrint();
            ++nCommas;
        }
        prettyWrite(")");
        prettyWrite(" : ");
        suite.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = new RuntimeFunc(outerName.toString(), innerNames, suite, curScope);
        curScope.assign(outerName.toString(), value);
        trace("def " + outerName.toString());
        return null;
    }
}