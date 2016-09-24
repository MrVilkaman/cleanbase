package ru.fixapp.fooproject.domainlayer.exceptions;

public class UncheckedException extends Throwable {

	public UncheckedException() {
		super();
	}

	public UncheckedException(String detailMessage) {
		super(detailMessage);
	}
}
