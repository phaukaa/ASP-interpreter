package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeNoneValue extends RuntimeValue {
    @Override
    protected String typeName() {
	    return "None";
    }


    @Override 
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	    return "None";
    }


    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
	    return false;
    }


    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
	    return new RuntimeBoolValue(v instanceof RuntimeNoneValue);
    }


    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	    return new RuntimeBoolValue(true);
    }


    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
	    return new RuntimeBoolValue(!(v instanceof RuntimeNoneValue));
    }
}
