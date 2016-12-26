package com.github.mrvilkaman.testsutils;


import static org.mockito.Mockito.mock;

public class Tutils {

	public static <T> T mockBuilder(Class<T> classToMock) {
		return mock(classToMock,new AnswerWithSelf(classToMock));
	}
}
