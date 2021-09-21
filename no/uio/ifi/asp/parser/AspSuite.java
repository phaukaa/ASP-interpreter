package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax {

    ArrayList<AspStmt> stmt = new ArrayList<AspStmt>();
    AspSmallStmtList stmtList = null;

    AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s) {
        enterParser("suite");
        AspSuite as = new AspSuite(s.curLineNum());
        if(s.curToken().kind == newLineToken) {
            skip(s, newLineToken);
            skip(s, indentToken);
            while(s.curToken().kind != dedentToken) {
                
                // Edge case judgement call
                // Since blank lines are ignored and wont generate dedenttokens, an eof token will act as a dedenttoken to keep the program from crashing when reaching eof
                if(s.curToken().kind == eofToken) {
                    leaveParser("suite");
                    return as;
                }
                as.stmt.add(AspStmt.parse(s));
            }
            skip(s, dedentToken);
        }
        else {
            as.stmtList = AspSmallStmtList.parse(s);
        }
        leaveParser("suite");
        return as;
    }

    @Override
    void prettyPrint() {
        if (stmtList != null) {
            stmtList.prettyPrint();
        }
        else {
            prettyWriteLn();
            prettyIndent();
            for (AspStmt s: stmt) {
                s.prettyPrint();
            }
            prettyDedent();
        }
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if(stmtList != null) {
            stmtList.eval(curScope);
        } else {
            for(AspStmt s : stmt) {
                s.eval(curScope);
            }
        }
        return null;
    }
}