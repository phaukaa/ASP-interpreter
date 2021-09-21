package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspCompoundStmt extends AspStmt {

    AspCompoundStmt(int n) {
        super(n);
    }

    static AspCompoundStmt parse(Scanner s) {
        enterParser("compound stmt");
        AspCompoundStmt acs = null;
        switch(s.curToken().kind) {
            case forToken:
                acs = AspForStmt.parse(s); break;
            case ifToken:
                acs = AspIfStmt.parse(s); break;
            case whileToken:
                acs = AspWhileStmt.parse(s); break;
            case defToken:
                acs = AspFuncDef.parse(s); break;
            default:
                parserError("Expected an expression compound but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("compound stmt");
        return acs;
    }
}