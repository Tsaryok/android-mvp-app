package com.example.walker.utils;

public final class AppConstants {

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "mindorks_mvp.db";
    public static final String PREF_NAME = "mindorks_pref";

    public static final long NULL_INDEX = -1L;

    public static final String SEED_DATABASE_STORES = "seed/stores.json";
    public static final String SEED_DATABASE_PRODUCTS = "seed/products.json";
    public static final String SEED_DATABASE_STORE_TAGS = "seed/store_tags.json";
    public static final String SEED_DATABASE_JSWST = "seed/jswst.json";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
