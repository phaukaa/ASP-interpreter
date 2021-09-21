package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String stringValue;

    public RuntimeStringValue(String v) {
        stringValue = v;
    }

    @Override
    protected String typeName() {
	    return "String";
    }


    @Override 
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	    return "\"" + stringValue + "\"";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (stringValue != "") return true;
        return false; 
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            runtimeError("String '" + what + "' is not a legal int", where);
            return 0;
        }
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeStringValue(stringValue + v.getStringValue("+ operand", where));
        }
        runtimeError("Type error for +.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue == v.getStringValue("== operand", where));
        }
        runtimeError("Type error for ==.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            boolean b = stringValue.compareTo(v.getStringValue("< operand", where)) > 0;
            return new RuntimeBoolValue(b);
        }
        runtimeError("Type error for >.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            boolean b = stringValue.compareTo(v.getStringValue(">= operand", where)) >= 0;
            return new RuntimeBoolValue(b);
        }
        runtimeError("Type error for >=.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            boolean b = stringValue.compareTo(v.getStringValue("< operand", where)) < 0;
            return new RuntimeBoolValue(b);
        }
        runtimeError("Type error for <.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            boolean b = stringValue.compareTo(v.getStringValue("<= operand", where)) <= 0;
            return new RuntimeBoolValue(b);
        }
        runtimeError("Type error for <=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(stringValue.length());
    }
    
    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            String s = "";
            for (int i = 0; i < v.getIntValue("* operand", where); i++) {
                s += stringValue;
            }
            return new RuntimeStringValue(s);
        }
        runtimeError("Type error for *.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!this.getBoolValue("not operand", where));
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue != v.getStringValue("string !=", where));
        }
        runtimeError("Type error for !=.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            if (v.getIntValue("string subscription", where) >= stringValue.length()) {
                Main.panic("String index out of range!");
            }
            else {
                return new RuntimeStringValue(Character.toString(stringValue.charAt((int)v.getIntValue("string subscription", where))));
            }
        }
        runtimeError("Type error for *.", where);
        return null;
    }
}