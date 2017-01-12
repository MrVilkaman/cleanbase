package com.github.mrvilkaman.datalayer.api.response;

public interface IBaseResponse {

	int getCode();

	String getMessage();

	boolean isSuccess();
}
