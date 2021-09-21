package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspSmallStmt extends AspSyntax {

    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        enterParser("small stmt");
        AspSmallStmt ass = null;
        if (s.anyEqualToken()) {
            ass = AspAssignment.parse(s);
        }
        else {
            switch (s.curToken().kind) {
                case passToken:
                    ass = AspPassStmt.parse(s); break;
                case returnToken:
                    ass = AspReturnStmt.parse(s); break;
                default:
                    ass = AspExprStmt.parse(s);
            }
        }
        leaveParser("small stmt");
        return ass;
    }
}