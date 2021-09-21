package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspStmt extends AspSyntax {
    
    AspStmt(int n) {
        super(n);
    }

    static AspStmt parse(Scanner s) {
        enterParser("stmt");
        AspStmt as;
        switch (s.curToken().kind) {
            case forToken:
            case ifToken:
            case whileToken:
            case defToken:
                as = AspCompoundStmt.parse(s); break;
            default:
                as = AspSmallStmtList.parse(s);
        }
        leaveParser("stmt");
        return as;
    }
}