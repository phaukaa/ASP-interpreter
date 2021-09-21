package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	    super(n);
    }


    public static AspProgram parse(Scanner s) {
        enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
            ap.stmts.add(AspStmt.parse(s));
        }
        leaveParser("program");
        return ap;
    }


    @Override
    public void prettyPrint() {
        for (AspStmt stmt: stmts) {
            if (stmt != null) stmt.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	    for (AspStmt stmt : stmts) {
            try {
                stmt.eval(curScope);
            } catch (RuntimeReturnValue returnValue) {
                RuntimeValue.runtimeError("Return statement outside function!", returnValue.lineNum);
            }
        }
        return null;
    }
}
