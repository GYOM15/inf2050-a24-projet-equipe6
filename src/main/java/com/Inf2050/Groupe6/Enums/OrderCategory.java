package main.java.com.Inf2050.Groupe6.Enums;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

public enum OrderCategory {
    ARCHITECTES("architectes"),
    GEOLOGUES("géologues", "2021-2024"),
    PSYCHOLOGUES("psychologues", "2020-2025"),
    ORDER_NON_VALIDE("non-valide");

    private final String orderFromJsonObj;
    private final String[] listeCycle;
    private final String cycle = "";

    OrderCategory(String orderFromJsonObj, String... cycle){
        this.orderFromJsonObj = orderFromJsonObj;
        this.listeCycle = cycle;
    }

    public static String getCycle(){

        return this.cycle;
    }

    private void checkCycle(){

    }

    public static OrderCategory searchFromJsonOrder(String label, ErrorHandler errorHandler) {
        for (OrderCategory order : OrderCategory.values()) {
            if (order.orderFromJsonObj.equalsIgnoreCase(label)) {
                return order;
            }
        }
        addErrorIfHandlerPresent(label, errorHandler);
        return ORDER_NON_VALIDE;
    }

    private static void addErrorIfHandlerPresent(String label, ErrorHandler errorHandler) {
        if (errorHandler != null) {
            errorHandler.addError("L'ordre " + label +" n'est pas valide");
        }
    }
}
