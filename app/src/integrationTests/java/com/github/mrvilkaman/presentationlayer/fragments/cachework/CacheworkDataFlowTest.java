package com.github.mrvilkaman.presentationlayer.fragments.cachework;

import com.github.mrvilkaman.datalayer.provider.CacheworkDpImpl;
import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.interactor.cache.CacheworkInteractorImpl;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.testsutils.BaseTestCase;
import com.github.mrvilkaman.testsutils.GlobalSubscriptionManagerForTest;
import com.github.mrvilkaman.testsutils.TestSchedulers;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


public class CacheworkDataFlowTest extends BaseTestCase {

	@Mock CacheworkView view;
	@Mock ThrowableResolver throwableResolver;
	private CacheworkPresenter presenter;
	private CacheworkInteractorImpl interactor;
	private CacheworkDpImpl cacheworkDp;
	private TestSchedulers provider;

	@Override
	public void init() {
		provider = new TestSchedulers();

		cacheworkDp =
				Mockito.spy(new CacheworkDpImpl(new GlobalSubscriptionManagerForTest(), provider));
		interactor = new CacheworkInteractorImpl(cacheworkDp);
		presenter = new CacheworkPresenter(interactor);

		presenter.setView(view);
		presenter.setThrowableResolver(throwableResolver);
		presenter.setSchedulersProvider(provider);
	}

	@Test
	public void testOnViewAttached() {
		provider.setUseTestScheduler();
		// Arrange
		when(cacheworkDp.getUuid()).thenReturn("q1", "q2", "q3");
		// Act
		presenter.onViewAttached();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);

		// Assert
		InOrder inOrder = Mockito.inOrder(view);
		inOrder.verify(view).showProgress();
		inOrder.verify(view).bindText("q1");
		inOrder.verify(view).hideProgress();

	}

	@Test
	public void testUpdate() {
		provider.setUseTestScheduler();
		// Arrange
		when(cacheworkDp.getUuid()).thenReturn("q1", "q2", "q3");
		// Act
		presenter.onViewAttached();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);
		presenter.update();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);


		// Assert
		InOrder inOrder = Mockito.inOrder(view);
		inOrder.verify(view).showProgress();
		inOrder.verify(view).bindText("q1");
		inOrder.verify(view).hideProgress();

		inOrder.verify(view).showProgress();
		inOrder.verify(view).bindText("q2");
		inOrder.verify(view).hideProgress();

	}


	@Test
	public void testUpdate_withError() {
		provider.setUseTestScheduler();

		// Arrange
		when(cacheworkDp.getUuid()).thenReturn("q1", "q2", "q3");
		// Act
		presenter.onViewAttached();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);

		Observable<String> just = cacheworkDp.getJust();
		when(cacheworkDp.getJust()).thenReturn(Observable.error(new NotFoundException()));
		presenter.update();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);

		when(cacheworkDp.getJust()).thenReturn(just);
		presenter.update();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);

		// Assert
		InOrder inOrder = Mockito.inOrder(view,throwableResolver);
		inOrder.verify(view).showProgress();
		inOrder.verify(view).bindText("q1");
		inOrder.verify(view).hideProgress();

		inOrder.verify(view).showProgress();
		inOrder.verify(throwableResolver).handleError(org.mockito.Matchers.isA(NotFoundException.class));
		inOrder.verify(view).hideProgress();

		inOrder.verify(view).showProgress();
		inOrder.verify(view).bindText("q2");
		inOrder.verify(view).hideProgress();

	}


	@Test
	public void testReCreatete() {
		provider.setUseTestScheduler();
		// Arrange
		when(cacheworkDp.getUuid()).thenReturn("q1", "q2", "q3");
		// Act
		presenter.onViewAttached();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);
		presenter.onViewDetached();

		// Assert
		InOrder inOrder = Mockito.inOrder(view);
		inOrder.verify(view).showProgress();
		inOrder.verify(view).bindText("q1");
		inOrder.verify(view).hideProgress();

		presenter.onViewAttached();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);

		inOrder.verify(view).bindText("q1");
		inOrder.verify(view, times(2)).showProgress();
		inOrder.verify(view).bindText("q2");
		inOrder.verify(view).hideProgress();

	}



	@Test
	public void testUpdate2() {
		provider.setUseTestScheduler();
		// Arrange
		when(cacheworkDp.getUuid()).thenReturn("q1", "q2", "q3");
		// Act
		presenter.onViewAttached();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);
		presenter.update2();
		provider.getTestScheduler().advanceTimeBy(3, TimeUnit.SECONDS);


		// Assert
		InOrder inOrder = Mockito.inOrder(view);
		inOrder.verify(view).showProgress();
		inOrder.verify(view).bindText("q1");
		inOrder.verify(view).hideProgress();

		inOrder.verify(view).showProgress();
		inOrder.verify(view).bindText("q2");
		inOrder.verify(view).hideProgress();

	}

}