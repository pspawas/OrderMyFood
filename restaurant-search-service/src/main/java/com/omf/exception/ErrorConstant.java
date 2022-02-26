package com.omf.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorConstant {
    public static final Map<String, String> REST_ERRORS = new HashMap<>();

    public static final String REST90 = "REST90";
    public static final String REST100 = "REST100";
    public static final String MENU100 = "MENU100";
    public static final String MENU101 = "MENU101";
    public static final String MENU102 = "MENU102";
    public static final String REST101 = "REST101";
    public static final String REST102 = "REST102";
    public static final String REST103 = "REST103";
    public static final String REST104 = "REST104";
    private static final String MENU90 = "MENU90";

    static {
        REST_ERRORS.put(REST90, "Restaurant name already exist.");
        REST_ERRORS.put(REST100, "Invalid restaurant name given.");
        REST_ERRORS.put(REST101, "Invalid restaurant location given.");
        REST_ERRORS.put(REST102, "Restaurant restaurant distance given.");
        REST_ERRORS.put(REST103, "Restaurant restaurant cuisine given.");
        REST_ERRORS.put(REST104, "Restaurant restaurant budget given.");

        REST_ERRORS.put(MENU90, "Menu items already exist for given restaurant Id.");
        REST_ERRORS.put(MENU100, "Invalid menu name given.");
        REST_ERRORS.put(MENU101, "Invalid menu description given.");
        REST_ERRORS.put(MENU102, "Invalid menu price given.");
    }

    static List getError(String errorCode) {
        return List.of(RestaurantError.build(errorCode));
    }
}
