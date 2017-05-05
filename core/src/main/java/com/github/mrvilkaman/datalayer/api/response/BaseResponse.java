package com.github.mrvilkaman.datalayer.api.response;

public class BaseResponse implements IBaseResponse<Object> {
	private int code;
	private String message;

	public BaseResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return 200 <= code && code < 300;
	}

	@Override
	public Object getBody() {
		return null;
	}
}
