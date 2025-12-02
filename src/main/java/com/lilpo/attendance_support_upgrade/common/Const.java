package com.lilpo.attendance_support_upgrade.common;

public class Const {
    public static final String BASE_URL = "/api/attendance-support";


    public static final String CREATE_URL = "/create";
    public static final String SEARCH_URL = "/search";
    public static final String UPDATE_URL = "/update";
    public static final String DELETE_URL = "/delete";

    // excel
    public static final String IMPORT_URL = "/import";
    public static final String DOWNLOAD_TEMPLATE = "/import/download-template";
    public static final String DOWNLOAD_RESULT = "/import/download-result";
    public static final String VALIDATE_FILE_IMPORT = "/import/validate-file-import";
    public static final String FILE_EXCEL_EXTENSION = ".xlsx";


    public static final String USER_URL = "/users";


    public static final String SEARCH_URL_HISTORY = "/search-history";


    public static final String EXPORT_URL = "/export";


    public static final String HISTORY_URL = "/history/{id}";
    public static final String DETAIL_URL = "/detail/{id}";


    public static final int PAGE_MIN_SIZE = 1;
    public static final int PAGE_MAX_SIZE = 10000;
    public static final int PAGE_MIN_INDEX = 0;
    public static final int PAGE_MAX_INDEX = 1000000;
    public static final int PAGE_INDEX_DEFAULT = 1000000;


}
