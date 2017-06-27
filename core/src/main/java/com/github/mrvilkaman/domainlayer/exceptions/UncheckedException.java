package com.github.mrvilkaman.domainlayer.exceptions;

public class UncheckedException extends Throwable {

	public UncheckedException(String detailMessage) {
		super(detailMessage);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
