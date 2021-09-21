package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.lang.*;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    long intValue;

    public RuntimeIntValue(long v) {
        intValue = v;
    }

    @Override
    protected String typeName() {
	    return "Integer";
    }


    @Override 
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	    return Long.toString(intValue);
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double)intValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (intValue != 0) return true;
        return false;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return Long.toString(intValue);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue +  v.getIntValue("+ operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue + v.getFloatValue("+ operand", where));
        }
        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
	    if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue / v.getFloatValue("/ operand", where));
        }
        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue == v.getFloatValue("== operand", where));
        }
	    runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue > v.getFloatValue("> operand", where));
        }
	    runtimeError("Type error for >.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue >= v.getFloatValue(">= operand", where));
        }
	    runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(Math.floorDiv(intValue, v.getIntValue("// operand", where)));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("// operand", where)));
        }
        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue < v.getFloatValue("< operand", where));
        }
	    runtimeError("Type error for <.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue <= v.getFloatValue(">= operand", where));
        }
	    runtimeError("Type error for <=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(Math.floorMod(intValue, v.getIntValue("% operand", where)));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue - v.getFloatValue("% operand", where)*Math.floor(intValue/v.getFloatValue("% operand", where)));
        }
        runtimeError("Type error for %.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue * v.getIntValue("* operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue * v.getFloatValue("* operand", where));
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntValue(-intValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!this.getBoolValue("not operand", where));
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue != v.getFloatValue("== operand", where));
        }
	    runtimeError("Type error for !=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntValue(Math.abs(intValue));
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue -  v.getIntValue("- operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue - v.getFloatValue("- operand", where));
        }
        runtimeError("Type error for -.", where);
        return null;
    }
}