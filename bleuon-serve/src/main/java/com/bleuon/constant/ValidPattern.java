package com.bleuon.constant;

public class ValidPattern {

    /**
     * 邮箱验证码类型
     */
    public static final String MAIL_CODE_TYPE = "(register|login|reset)";

    /**
     * 6 位正整数
     */
    public static final String DIGIT_6 = "^\\d{6}$";

    public static final String PHONE = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    public static final String PASSWORD = "^[a-zA-Z0-9.]{8,16}$";

    public static final String ACCOUNT = "^(?:[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}|(?![_-])[\\u4e00-\\u9fa5a-zA-Z][\\u4e00-\\u9fa5a-zA-Z0-9_-]{4,16}|(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8})$";

    public static final String CELL_TYPE = "(basic|flowchart)";

    /**
     * 是否为 UUID
     */
    public static final String UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
}
