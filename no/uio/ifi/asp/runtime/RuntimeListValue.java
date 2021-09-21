package no.uio.ifi.asp.runtime;

import java.util.*;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    ArrayList<RuntimeValue> listValues = new ArrayList<>();

    public RuntimeListValue(ArrayList<RuntimeValue> v) {
        listValues = v;
    }
    
    @Override
    protected String typeName() {
        return "List";
    }

    public ArrayList<RuntimeValue> getList() {
        return listValues;
    }
    
    @Override 
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
        return listValues.toString();
    }
    
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return listValues.size() != 0;
    }
    
    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        long index = inx.getIntValue("assignment", where);
        listValues.set((int) index, val);
    }
    
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for list equal.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(listValues.size());
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            ArrayList<RuntimeValue> newList = new ArrayList<>();
            for (int i = 0; i < v.getIntValue("list multiplication", where); i++) {
                for (int j = 0; j < listValues.size(); j++) {
                    newList.add(listValues.get(j));
                }
            }
            return new RuntimeListValue(newList);
        }
        runtimeError("Type error for list multiplication.", where);
        return null;
    } 
    
    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!this.getBoolValue("not operand", where));
    }
    
    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true); 
        }
        runtimeError("Type error for list not equal.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            int index = (int)v.getIntValue("list subscription", where);
            int i;

            if (index < 0) i = index + listValues.size();
            else {i = index;}

            if (i >= listValues.size() || i < 0) runtimeError("List index " + i + "out of range!", where);

            return listValues.get(i);
        }
        runtimeError("Type error for list subscription.", where);
        return null;
    }
}