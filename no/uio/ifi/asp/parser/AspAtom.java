package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspAtom extends AspSyntax {

    AspAtom(int n) {
        super(n);
    }

    static AspAtom parse(Scanner s) {
        enterParser("atom");
        AspAtom aa = null;
        switch(s.curToken().kind) {
            case falseToken:
            case trueToken:
                aa = AspBooleanLiteral.parse(s); break;
            case floatToken:
                aa = AspFloatLiteral.parse(s); break;
            case integerToken:
                aa = AspIntegerLiteral.parse(s); break;
            case leftBraceToken:
                aa = AspDictDisplay.parse(s); break;
            case leftParToken:
                aa = AspInnerExpr.parse(s); break;
            case nameToken:
                aa = AspName.parse(s); break;
            case noneToken:
                aa = AspNoneLiteral.parse(s); break;
            case stringToken:
                aa = AspStringLiteral.parse(s); break;
            case leftBracketToken:
                aa = AspListDisplay.parse(s); break;
            default:
                parserError("Expected an expression atom but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("atom");
        return aa;
    }
}