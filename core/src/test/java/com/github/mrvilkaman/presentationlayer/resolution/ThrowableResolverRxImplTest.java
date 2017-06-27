package com.github.mrvilkaman.presentationlayer.resolution;

import com.github.mrvilkaman.testsutils.BaseTestCase;
import com.github.mrvilkaman.testsutils.TestSchedulers;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ThrowableResolverRxImplTest extends BaseTestCase {

	@Mock ThrowableResolver throwableResolver;
	private ThrowableResolverRxImpl resolver;

	@Override
	public void init() {
		resolver = new ThrowableResolverRxImpl(throwableResolver,new TestSchedulers());

		resolver.init();
	}

	@Test
	public void testHandleError_one() {
		resolver.handleError(new RuntimeException());

		verify(throwableResolver).handleError(any(RuntimeException.class));
	}

	@Test
	public void testHandleError() {
		resolver.handleError(new RuntimeException("1"));
		resolver.handleError(new RuntimeException("1"));
		resolver.handleError(new RuntimeException("1"));

		ArgumentCaptor<Throwable> argument = ArgumentCaptor.forClass(Throwable.class);
		verify(throwableResolver).handleError(argument.capture());
		assertThat(argument.getValue()).hasMessage("1");

	}

	@Test
	public void testHandleError_diff() {
		resolver.handleError(new RuntimeException());
		resolver.handleError(new IOException());

		ArgumentCaptor<Throwable> argument = ArgumentCaptor.forClass(Throwable.class);
		verify(throwableResolver, times(2)).handleError(argument.capture());
		List<Throwable> allValues = argument.getAllValues();
		assertThat(allValues.get(0)).isInstanceOf(RuntimeException.class);
		assertThat(allValues.get(1)).isInstanceOf(IOException.class);
	}


//
//
//	@Test
//	public void testHandleError_diff_2() {
//		resolver.handleError(new RuntimeException());
//		resolver.handleError(new IOException());
//		resolver.handleError(new RuntimeException());
//		resolver.handleError(new IOException());
//		resolver.handleError(new IOException());
//
//		ArgumentCaptor<Throwable> argument = ArgumentCaptor.forClass(Throwable.class);
//		verify(throwableResolver, times(2)).handleError(argument.capture());
//		List<Throwable> allValues = argument.getAllValues();
//		assertThat(allValues.get(0)).isInstanceOf(RuntimeException.class);
//		assertThat(allValues.get(1)).isInstanceOf(IOException.class);
//	}




}