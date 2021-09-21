package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        assign("float", new RuntimeFunc("float") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                trace("float", actualParams, where);
                checkNumParams(actualParams, 1, "float", where);
                double floatValue = actualParams.get(0).getFloatValue("float", where);
                return new RuntimeFloatValue(floatValue);
            }
        });

        assign("input", new RuntimeFunc("input") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                trace("input", actualParams, where);
                checkNumParams(actualParams, 1, "input", where);
                System.out.print(actualParams.get(0).getStringValue("input", where));
                String input = keyboard.nextLine();
                return new RuntimeStringValue(input);
            }
        });

        assign("int", new RuntimeFunc("int") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                trace("int", actualParams, where);
                checkNumParams(actualParams, 1, "int", where);
                long intValue = actualParams.get(0).getIntValue("int", where);
                return new RuntimeIntValue(intValue);
            }
        });

        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                trace("len", actualParams, where);
                checkNumParams(actualParams, 1, "len", where);
                return actualParams.get(0).evalLen(where);
            }
        });

        assign("print", new RuntimeFunc("print") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                trace("print", actualParams, where);
                for (RuntimeValue value : actualParams) {
                    System.out.print(value.getStringValue("print", where) + " ");
                }
                System.out.println();
                return new RuntimeNoneValue();
            }

        });

        assign("range", new RuntimeFunc("range") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                trace("range", actualParams, where);
                checkNumParams(actualParams, 2, "range", where);

                long start = actualParams.get(0).getIntValue("range start", where);
                long end = actualParams.get(1).getIntValue("range end", where);

                ArrayList<RuntimeValue> elements = new ArrayList<>();
                for (long i = start; i < end; i++) {
                    elements.add(new RuntimeIntValue(i));
                }

                return new RuntimeListValue(elements);
            }
        });

        assign("str", new RuntimeFunc("str") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                trace("str", actualParams, where);
                checkNumParams(actualParams, 1, "str", where);
                return new RuntimeStringValue(actualParams.get(0).getStringValue("str", where));
            }
        });

        assign("exit", new RuntimeFunc("exit") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                System.exit((int)actualParams.get(0).getIntValue("exit", where));
                return null; //Required by the compiler
            }
        });
    }


    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
        if (actArgs.size() != nCorrect) RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }

    private void trace(String name, ArrayList<RuntimeValue> actualParams, AspSyntax where) {
        RuntimeListValue runtimeParams = new RuntimeListValue(actualParams);
        Main.log.traceEval("Call function " + name + " with params " + runtimeParams.showInfo(), where);
    }
}
