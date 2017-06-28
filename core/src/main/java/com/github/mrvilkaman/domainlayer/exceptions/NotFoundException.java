package com.github.mrvilkaman.domainlayer.exceptions;

public class NotFoundException extends Throwable {

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
