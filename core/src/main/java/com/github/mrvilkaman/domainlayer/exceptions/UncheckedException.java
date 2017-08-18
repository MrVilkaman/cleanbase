package com.github.mrvilkaman.domainlayer.exceptions;

import android.support.annotation.Nullable;

public class UncheckedException extends Throwable {

	@Nullable private final Object body;

	public UncheckedException(@Nullable String detailMessage) {
		this(detailMessage,null);
	}

	public UncheckedException(@Nullable String detailMessage,@Nullable Object body) {
		super(detailMessage);
		this.body = body;
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
