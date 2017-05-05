package com.github.mrvilkaman.domainlayer.exceptions;

public class ServerException extends Throwable {

	public ServerException(String message, Throwable throwable) {
		super(message,throwable);
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
}
