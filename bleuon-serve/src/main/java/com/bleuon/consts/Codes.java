package com.bleuon.consts;

public class Codes {

    // 请求正常
    public static int SUCCESS = 20000;

    // Token 过期或不存在
    public static int AUTH_TOKEN_NONE_OR_EXPIRE = 40100;

    // Token 存在，但是权限不够
    public static int AUTH_NOT_ENOUGH = 40101;

    // 未知错误，一般是在校验身份时的错误，可能是 Token 的解析错误，或者是连接 Redis、MySQL 失败的错误
    public static int AUTHORITY_UNKNOWN_ERROR = 40102;

    // 密码或用户名错误
    public static int PASSWORD_OR_USERNAME_ERROR = 40200;

}
