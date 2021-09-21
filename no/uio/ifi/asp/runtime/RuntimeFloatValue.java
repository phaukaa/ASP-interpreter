package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    protected String typeName() {
	    return "Float";
    }


    @Override 
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	    return Double.toString(floatValue);
    }
    
    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (floatValue != 0.0) return true;
        return false; 
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue +  v.getIntValue("+ operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue + v.getFloatValue("+ operand", where));
        }
        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
	    if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operand", where));
        }
        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue == v.getFloatValue("== operand", where));
        }
	    runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue > v.getFloatValue("> operand", where));
        }
	    runtimeError("Type error for >.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue >= v.getFloatValue(">= operand", where));
        }
	    runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(floatValue / v.getFloatValue("// operand", where)));
        }
        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue < v.getFloatValue("> operand", where));
        }
	    runtimeError("Type error for <", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue <= v.getFloatValue(">= operand", where));
        }
	    runtimeError("Type error for <=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue - v.getFloatValue("% operand", where)*Math.floor(floatValue/v.getFloatValue("% operand", where)));
        }
        runtimeError("Type error for %.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue*v.getFloatValue("* operand", where));
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(floatValue*=-1);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!this.getBoolValue("not operand", where));
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue != v.getFloatValue("== operand", where));
        }
	    runtimeError("Type error for !=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(Math.abs(floatValue));
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue - v.getFloatValue("- operand", where));
        }
        runtimeError("Type error for -.", where);
        return null;
    }
}