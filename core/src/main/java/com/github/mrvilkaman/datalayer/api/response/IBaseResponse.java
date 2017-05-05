package com.github.mrvilkaman.datalayer.api.response;

public interface IBaseResponse<Body> {

	int getCode();

	String getMessage();

	boolean isSuccess();

	Body getBody();
}
