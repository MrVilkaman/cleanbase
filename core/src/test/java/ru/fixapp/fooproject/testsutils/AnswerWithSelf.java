package ru.fixapp.fooproject.testsutils;


import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class AnswerWithSelf implements Answer<Object> {
	private final Answer<Object> delegate = new ReturnsEmptyValues();
	private final Class<?> clazz;

	public AnswerWithSelf(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Object answer(InvocationOnMock invocation) throws Throwable {
		Class<?> returnType = invocation.getMethod().getReturnType();
		if (returnType == clazz) {
			return invocation.getMock();
		} else {
			return delegate.answer(invocation);
		}
	}
}