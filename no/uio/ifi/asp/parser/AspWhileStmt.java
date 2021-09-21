package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspCompoundStmt {

    AspExpr test;
    AspSuite body;

    AspWhileStmt(int n) {
        super(n);
    }

    static AspWhileStmt parse(Scanner s) {
        enterParser("while");
        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
        skip(s, whileToken);
        aws.test = AspExpr.parse(s);
        skip(s, colonToken);
        aws.body = AspSuite.parse(s);
        leaveParser("while");

        return aws;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" while ");
        test.prettyPrint();
        prettyWrite(" : ");
        body.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        while(test.eval(curScope).getBoolValue("while statement", this)) {
            trace("while true ...");
            body.eval(curScope);
        }
        trace("while false ...");
        return null;
    }
}