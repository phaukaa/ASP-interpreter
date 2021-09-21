package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspStmt {

    ArrayList<AspSmallStmt> smallStmtList = new ArrayList<>();
    Boolean lastSemicolon = false;
    
    AspSmallStmtList(int n) {
        super(n);
    }

    static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList assl = new AspSmallStmtList(s.curLineNum());

        assl.smallStmtList.add(AspSmallStmt.parse(s));
        while (s.curToken().kind == semicolonToken) {
            skip(s, semicolonToken);
            if (s.curToken().kind == newLineToken) break;
            assl.smallStmtList.add(AspSmallStmt.parse(s));
        }

        if (s.curToken().kind == semicolonToken) {
            skip(s, semicolonToken);
            assl.lastSemicolon = true;
        }
        skip(s, newLineToken);
        leaveParser("small stmt list");
        return assl;
    }

    @Override
    void prettyPrint() {
        for(AspSmallStmt n : smallStmtList) {
            if(smallStmtList.indexOf(n) == smallStmtList.size()-1 && !lastSemicolon) {
                n.prettyPrint();
            } else {
                n.prettyPrint();
                prettyWrite("; ");
            }
        }
        prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspSmallStmt stmt : smallStmtList) {
            stmt.eval(curScope);
        }
        return null;
    }
}