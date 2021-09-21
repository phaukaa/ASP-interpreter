package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspAssignment extends AspSmallStmt {

    AspName an;
    ArrayList<AspSubscription> subscriptions = new ArrayList<AspSubscription>();
    AspExpr ae;
    
    AspAssignment(int n) {
        super(n);
    }

    static AspAssignment parse(Scanner s) {
        enterParser("assignment");
        AspAssignment as = new AspAssignment(s.curLineNum());
        as.an = AspName.parse(s);
        while(s.curToken().kind != equalToken) {
            as.subscriptions.add(AspSubscription.parse(s));
        }
        skip(s, equalToken);
        as.ae = AspExpr.parse(s);
        
        leaveParser("assignment");
        return as;
    }

    @Override
    void prettyPrint() {
        an.prettyPrint();
        for(AspSubscription n : subscriptions) {
            n.prettyPrint();
        }
        prettyWrite(" = ");
        ae.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = ae.eval(curScope);
        String s = ae.toString();

        if(subscriptions.size() == 0) {
            curScope.assign(an.toString(), value);
        } 
        else {
            RuntimeValue v = curScope.find(an.toString(), this);
            for (int i = 0; i < subscriptions.size() - 1; i++) {
                v = v.evalSubscription(subscriptions.get(i).eval(curScope), this);
                s = s + "[" + v.showInfo() + "]";
            }
            RuntimeValue subscription = subscriptions.get(subscriptions.size() - 1).eval(curScope);
            s = s + "[" + subscription.showInfo() + "]";
            v.evalAssignElem(subscription, value, this);
        }
        s = s + " = " + value.showInfo();
        trace(s);
        return null;
    }
}