package com.github.mrvilkaman.testsutils;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;


/**
 * Created by Zahar on 19.03.16.
 */
public class TestSchedulers implements SchedulersProvider {

	private Scheduler immediate = Schedulers.trampoline();
	private TestScheduler testScheduler = new TestScheduler();

	private boolean useTestScheduler = false;

	@Override
	public Scheduler mainThread() {
		return getScheduler();
	}

	@Override
	public Scheduler io() {
		return getScheduler();
	}

	@Override
	public Scheduler computation() {
		return getScheduler();
	}

	@Override
	public Scheduler immediate() {
		return getScheduler();
	}


	public TestScheduler getTestScheduler() {
		return testScheduler;
	}

	public void setUseTestScheduler() {
		this.useTestScheduler = true;
	}


	private Scheduler getScheduler() {return useTestScheduler ? testScheduler : immediate;}

}
