package com.github.mrvilkaman.presentationlayer.resolution;

import com.github.mrvilkaman.datalayer.providers.GlobalBusQuery;
import com.github.mrvilkaman.testsutils.BaseTestCase;
import com.github.mrvilkaman.testsutils.TestSchedulers;
import com.github.mrvilkaman.utils.bus.Bus;
import com.github.mrvilkaman.utils.bus.RxBus;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ThrowableResolverRxImplTest extends BaseTestCase {

	@Mock ThrowableResolver throwableResolver;
	private ThrowableResolverRxImpl resolver;
	private Bus bus = RxBus.create();
	private TestSchedulers provider;

	@Override
	public void init() {
		provider = new TestSchedulers();
		resolver = new ThrowableResolverRxImpl(throwableResolver, bus, provider);

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
		resolver.handleError(new RuntimeException("2"));
		resolver.handleError(new RuntimeException("3"));

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


	@Test
	public void testHandleError_bus_one() {
		bus.publish(GlobalBusQuery.GLOBAL_ERRORS, new RuntimeException());

		verify(throwableResolver).handleError(any(RuntimeException.class));
	}

	@Test
	public void testHandleError_bus_diff() {
		resolver.handleError(new RuntimeException());
		bus.publish(GlobalBusQuery.GLOBAL_ERRORS, new IOException());


		ArgumentCaptor<Throwable> argument = ArgumentCaptor.forClass(Throwable.class);
		verify(throwableResolver, times(2)).handleError(argument.capture());
		List<Throwable> allValues = argument.getAllValues();
		assertThat(allValues.get(0)).isInstanceOf(RuntimeException.class);
		assertThat(allValues.get(1)).isInstanceOf(IOException.class);
	}




	@Test
	public void testHandleError_bus() {
		resolver.handleError(new RuntimeException("1"));
		resolver.handleError(new RuntimeException("2"));
		resolver.handleError(new RuntimeException("3"));
		bus.publish(GlobalBusQuery.GLOBAL_ERRORS, new RuntimeException("4"));


		ArgumentCaptor<Throwable> argument = ArgumentCaptor.forClass(Throwable.class);
		verify(throwableResolver).handleError(argument.capture());
		assertThat(argument.getValue()).hasMessage("1");

	}

	@Test
	public void testHandleError_diff_2() {
		resolver.handleError(new RuntimeException());
		resolver.handleError(new IOException());
		resolver.handleError(new RuntimeException());
		resolver.handleError(new IOException());
		resolver.handleError(new IOException());

		ArgumentCaptor<Throwable> argument = ArgumentCaptor.forClass(Throwable.class);
		verify(throwableResolver, times(2)).handleError(argument.capture());
		List<Throwable> allValues = argument.getAllValues();
		assertThat(allValues.get(0)).isInstanceOf(RuntimeException.class);
		assertThat(allValues.get(1)).isInstanceOf(IOException.class);
	}



	@Test
	public void testHandleError_diff_with_busAndDelay() {
		provider.setUseTestScheduler();
		TestScheduler testScheduler = provider.getTestScheduler();

		resolver.handleError(new RuntimeException());
		resolver.handleError(new IOException());
		resolver.handleError(new RuntimeException());
		testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS);
		resolver.handleError(new IOException());
		resolver.handleError(new IOException());

		ArgumentCaptor<Throwable> argument = ArgumentCaptor.forClass(Throwable.class);
		verify(throwableResolver, times(3)).handleError(argument.capture());
		List<Throwable> allValues = argument.getAllValues();
		assertThat(allValues.get(0)).isInstanceOf(RuntimeException.class);
		assertThat(allValues.get(1)).isInstanceOf(IOException.class);
		assertThat(allValues.get(2)).isInstanceOf(IOException.class);
	}

}