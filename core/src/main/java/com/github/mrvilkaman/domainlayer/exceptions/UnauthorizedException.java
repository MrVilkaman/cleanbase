package com.github.mrvilkaman.domainlayer.exceptions;

public class UnauthorizedException extends Throwable {

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
