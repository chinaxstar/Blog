package cn.xstar.samplespringboot.util;

/**
 * 静态常量类
 *
 * @author xstar
 */
public class Const {
    public static final String MSG = "msg";
    public static final String MODEL_DATA = "data";
    public static final String SESSION_USER = "user";
    public static final String COOKIE_TICKET = "ticket";

    /**
     * 接口成功
     */
    public static final int SUCCESS = 666;
    /**
     * 接口失败
     */
    public static final int FAILURE = 555;
    /***********登录相关**************/
    public static final int LOGIN_NO_USER = 2001;
    public static final int LOGIN_WRONG_PASSSWD = 2002;
    public static final int LOGIN_NAME_EMPTY = 2003;
    public static final int LOGIN_PASSWD_EMPTY = 2004;
    /**********************************/
    public static final int JSON_PARSE_ERROR = 3001;
    /******************资源名称**********************/
    public static final String LOGIN_SERVICE_NAME = "cn.xstar.samplespringboot.LoginRestService";

}
