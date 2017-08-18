package com.github.mrvilkaman.domainlayer.exceptions;

import android.support.annotation.Nullable;

public class ServerException extends Throwable {

	private Object body;

	public ServerException(@Nullable String message, @Nullable Throwable throwable) {
		this(message, throwable, null);
	}

	public ServerException(@Nullable String message, @Nullable Throwable throwable, @Nullable
			Object body) {
		super(message, throwable);
		this.body = body;
	}

	public ServerException() {
		super();
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

	@Nullable
	public Object getBody() {
		return body;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
