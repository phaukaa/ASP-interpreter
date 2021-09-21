package no.uio.ifi.asp.runtime;

import java.util.*;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    HashMap<String, RuntimeValue> dictValues;

    public RuntimeDictValue(HashMap<String, RuntimeValue> v) {
        dictValues = v;
    }

    @Override
    protected String typeName() {
	    return "Dict";
    }


    @Override 
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	    return dictValues.toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !dictValues.isEmpty();
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        String index = inx.getStringValue("assignment", where);
        dictValues.put(index, val);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!this.getBoolValue("not operand", where));
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(dictValues.size());
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            RuntimeValue value = dictValues.get(v.getStringValue("dict subscription", where));
            if (!(value == null)) return value;
        }
        runtimeError("Type error for dictionary subscription.", where);
        return null;
    }
}