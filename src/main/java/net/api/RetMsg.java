package net.api;

import java.util.HashMap;
import java.util.Map;

public class RetMsg {

	public static final Map<Integer, String> retMsg = new HashMap<>();

	public static final int API_SUCCESS = 0;
	public static final int UNKNOWN_ERROR = 100;
	public static final int API_NOT_FOUND = 101;
	public static final int METHOD_NOT_FOUND = 102;
	public static final int PARAM_FORMAT_ERROR = 103;
	public static final int PARAM_CAN_NOT_BE_NULL = 104;
	public static final int VERSION_TOO_LOW = 105;
	public static final int VERSION_INVALID = 106;

	public static final int TOKEN_INVALID = 201;
	public static final int TOKEN_EXPIRED = 202;

	public static final int ACCOUNT_NOT_EXIST = 301;
	public static final int ACCOUNT_NOT_LOCKED = 302;
	public static final int ACCOUNT_PWD_ERROR = 303;
	public static final int IP_INVALID = 304;
	public static final int NOT_AUTHORIZED = 305;
    public static String getMsg(Integer iret){
    	return retMsg.get(iret);
    }
	static {
		retMsg.put(API_SUCCESS, "成功");
		retMsg.put(UNKNOWN_ERROR, "未知错误");
		retMsg.put(API_NOT_FOUND, "API 不存在");
		retMsg.put(METHOD_NOT_FOUND, "方法 : %s 不存在");
		retMsg.put(PARAM_FORMAT_ERROR, "参数 : %s 格式错误");
		retMsg.put(PARAM_CAN_NOT_BE_NULL, "参数 : %s 不能为空");
		retMsg.put(VERSION_TOO_LOW, "版本太低");
		retMsg.put(VERSION_INVALID, "版本无效");

		retMsg.put(TOKEN_INVALID, "令牌无效");
		retMsg.put(TOKEN_EXPIRED, "令牌过期");

		retMsg.put(ACCOUNT_NOT_EXIST, "帐号不存在");
		retMsg.put(ACCOUNT_NOT_LOCKED, "帐号锁定");
		retMsg.put(ACCOUNT_PWD_ERROR, "帐号或密码错误");
		retMsg.put(IP_INVALID, "IP无效");
		retMsg.put(NOT_AUTHORIZED, "方法 : %s 未获得授权");

	}
}