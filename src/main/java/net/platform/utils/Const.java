package net.platform.utils;

import java.util.HashMap;
import java.util.Map;

public class Const {

    public static final Map<Integer, String> codeMap = new HashMap<>();
    //用户已启用
    public static final String USER_START		= "1";
    //用户已注销
    public static final String USER_CANCLED       = "2";
    //用户已锁定
    public static final String USER_LOCKED       = "3";
    
    public static final String APP_ID		= "1000";
    //角色启用
    public static final String ROLE_START		= "1";
    //角色禁用
    public static final String ROLE_FORBIDDEN		= "0";
    //管理员角色
    public static final String ADMIN_ROLE		= "1";
    //默认密码
    public static final String DEFAULT_PASSWORD		= "888888";
    
    //用户头像保存地址
    public static final String USER_PHOTO		= "/upload/user_img/";
    //机构徽标保存地址
    public static final String ORGAN_LOGO		= "/upload/organ_img/logo/";
    //机构地图保存地址
    public static final String ORGAN_MAP		= "/upload/organ_img/map/";
    //机构展示图保存地址
    public static final String ORGAN_IMAGES		= "/upload/organ_img/images/";
    //信息标题图片保存地址
    public static final String TITLE_IMG		= "/upload/news/title_img";
    //咨询内容附件保存地址
    public static final String CONTENT_ATTACH	= "/upload/news/content_attach";
    //视频保存地址
    public static final String VIDEO_URL		= "/upload/news/video";
    //图集保存地址
    public static final String ATLAS_IMAGE		= "/upload/news/atlas_image";

    
    /**百度地图key */
    public static final String BAIDU_MAP_KEY	= "8ZCnTRGaKK31YVGNhEaveGOw";
    /**百度地图版本 */
    public static final String BAIDU_MAP_VERSION	= "2.0";
    
    public static final int CREATED_SUCCESS       = 201;
    public static final int UNKNOWN_ERROR         = 1000;
    public static final int API_NOT_FOUND         = 1001;
    public static final int API_CAN_NOT_BE_NULL   = 1002;
    public static final int PARAM_FORMAT_ERROR    = 1003;
    public static final int PARAM_CAN_NOT_BE_NULL = 1004;
    public static final int VERSION_IS_TOO_LOW    = 1005;
    public static final int REQUEST_MODE_ERROR    = 1006;
    public static final int API_SERVER_ERROR      = 1007;

    static {
        codeMap.put(CREATED_SUCCESS, "created success");
        codeMap.put(UNKNOWN_ERROR, "unknown error");
        codeMap.put(API_NOT_FOUND, "the api can't be found");
        codeMap.put(API_CAN_NOT_BE_NULL, "can't request without a api name");
        codeMap.put(PARAM_FORMAT_ERROR, "param : %s format error");
        codeMap.put(PARAM_CAN_NOT_BE_NULL, "param : %s can't be null");
        codeMap.put(VERSION_IS_TOO_LOW, "version is too low, please update your client");
        codeMap.put(REQUEST_MODE_ERROR, "the http request method is not allow");
        codeMap.put(API_SERVER_ERROR, "api server error");
    }
}