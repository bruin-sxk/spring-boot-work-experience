package com.sxk.gateway.vo;

public class ApiResponse<T> {
	// 响应码：SUCCESS为成功；SYS_USER_NOT_LOGIN为用户未登录；其他当系统错误处理
	private String resultCode;

	// 响应消息
	private String message;

	// 响应实体
	private T body;

	public ApiResponse(String resultCode, String message) {
		this.resultCode = resultCode;
		this.message = message;
	}

	public ApiResponse(String  result) {
		this("result.getResultCode()", "result.getMessage()");
	}

	public ApiResponse() {
		this("(Result) ResultCodeEnum.SUCCESS");
	}

	public ApiResponse(T body) {
		this();
		this.body = body;
	}

	public static ApiResponse success() {
		return new ApiResponse();
	}

	public static <T> ApiResponse<T> success(T body) {
		return new ApiResponse(body);
	}

	public static ApiResponse failResponse() {
		return new ApiResponse("ResultCodeEnum.SYS_ERR");
	}

	public static ApiResponse failResponse(String message) {
		ApiResponse apiResponse = new ApiResponse("ResultCodeEnum.SYS_ERR");
		apiResponse.setMessage(message);
		return apiResponse;
	}


	public String getResultCode() {
		return this.resultCode;
	}

	public String getMessage() {
		return this.message;
	}

	public T getBody() {
		return this.body;
	}

	public void setResultCode(final String resultCode) {
		this.resultCode = resultCode;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setBody(final T body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ApiResponse(resultCode=" + this.getResultCode() + ", message=" + this.getMessage() + ", body="
				+ this.getBody() + ")";
	}
}
