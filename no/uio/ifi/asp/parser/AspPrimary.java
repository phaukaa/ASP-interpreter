package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPrimary extends AspSyntax {
    
    AspAtom atom;
    ArrayList<AspPrimarySuffix> suffix = new ArrayList<>();

    AspPrimary(int n) {
        super(n);
    }

    static AspPrimary parse(Scanner s) {
        enterParser("primary");
        AspPrimary ap = new AspPrimary(s.curLineNum());
        ap.atom = AspAtom.parse(s);
        while (s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken) {
            ap.suffix.add(AspPrimarySuffix.parse(s));
        }
        leaveParser("primary");
        return ap;
    }

    @Override
    void prettyPrint() {
        atom.prettyPrint();
        for (AspPrimarySuffix aps: suffix) {
            aps.prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope scope) throws RuntimeReturnValue {
        RuntimeValue value = atom.eval(scope);
        for (AspPrimarySuffix s: suffix) {
            if (s instanceof AspSubscription) {
                RuntimeValue sub = s.eval(scope);
                value = value.evalSubscription(sub, this);
            }
            else {
                RuntimeValue arguments = s.eval(scope);
                ArrayList<RuntimeValue> argumentsList = ((RuntimeListValue)arguments).getList();
                value = value.evalFuncCall(argumentsList, this);
            }
        }
        return value;
    }
}