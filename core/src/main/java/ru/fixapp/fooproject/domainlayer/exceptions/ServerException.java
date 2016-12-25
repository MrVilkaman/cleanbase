package ru.fixapp.fooproject.domainlayer.exceptions;

public class ServerException extends Throwable {
	public ServerException(Throwable throwable) {
		super(throwable);
	}
}
