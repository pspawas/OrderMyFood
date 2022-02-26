package com.omf.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorConstant {

    private static final Map<String, String> ITEM_ERROR = new HashMap<>();
    public static final String ORDER90 = "ORDER90";
    public static final String ORDER92 = "ORDER92";
    public static final String ORDER105 = "ORDER105";
    public static final String ORDER106 = "ORDER106";
    public static final String ORDER107 = "ORDER107";
    public static final String CUSTOMER91 = "CUSTOMER91";
    public static final String ORDER91 = "ORDER91";
    public static final String ITEM91 = "ITEM91";
    private static final Map<String, String> MENU_ERRORS = new HashMap<>();
    private static final Map<String, String> CUSTOMER_ERROR = new HashMap<>();
    private static final String ORDER100 = "ORDER100";
    private static final Map<String, String> ORDER_ERRORS = new HashMap<>();
    private static final String MENU90 = "MENU90";
    private static final String MENU100 = "MENU100";
    private static final String MENU101 = "MENU101";
    private static final String MENU102 = "MENU102";
    private static final String ORDER101 = "ORDER101";
    private static final String ORDER102 = "ORDER102";
    private static final String ORDER103 = "ORDER103";
    private static final String ORDER104 = "ORDER104";

    static {
        ORDER_ERRORS.put(ORDER90, "Menu item with customer already exist.");
        ORDER_ERRORS.put(ORDER91, "Restaurant not found.");
        ORDER_ERRORS.put(ORDER92, "Customer/some orders not found.");
        ORDER_ERRORS.put(ORDER100, "Invalid restaurant name given.");
        ORDER_ERRORS.put(ORDER101, "Invalid restaurant location given.");
        ORDER_ERRORS.put(ORDER102, "Restaurant restaurant distance given.");
        ORDER_ERRORS.put(ORDER103, "Restaurant restaurant cuisine given.");
        ORDER_ERRORS.put(ORDER104, "Restaurant restaurant budget given.");
        ORDER_ERRORS.put(ORDER105, "Menu item's change not found.");
        ORDER_ERRORS.put(ORDER106, "Orders already canceled.");
        ORDER_ERRORS.put(ORDER107, "Some orders canceled and some not found.");

        CUSTOMER_ERROR.put(CUSTOMER91, "Customer not found.");

        ITEM_ERROR.put(ITEM91, "Customer/Item not found.");

        MENU_ERRORS.put(MENU90, "Menu items already exist for given restaurant ID.");
        MENU_ERRORS.put(MENU100, "Invalid menu name given.");
        MENU_ERRORS.put(MENU101, "Invalid menu description given.");
        MENU_ERRORS.put(MENU102, "Invalid menu price given.");
    }

    static List getError(String errorCode) {
        return List.of(OrderError.build(errorCode, ErrorConstant.ORDER_ERRORS.get(errorCode)));
    }
}
