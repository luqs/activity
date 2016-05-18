package com.cuize.activity.web.vo.common;

/**
 * 通用反馈对象
 * @author JackieLan
 *
 */
public class CommonRsp {

	private boolean success; // 是否成功
	
	private String resultCode; // 返回码
	
	private String resultMsg; // 提示信息
	
	private Object data; // 业务数据

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
