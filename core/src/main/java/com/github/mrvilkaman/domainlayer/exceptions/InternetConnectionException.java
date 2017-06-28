package com.github.mrvilkaman.domainlayer.exceptions;

public class InternetConnectionException extends Throwable {
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
