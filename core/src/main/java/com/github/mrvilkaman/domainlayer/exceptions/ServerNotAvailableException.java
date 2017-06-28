package com.github.mrvilkaman.domainlayer.exceptions;

public class ServerNotAvailableException extends Throwable {

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
