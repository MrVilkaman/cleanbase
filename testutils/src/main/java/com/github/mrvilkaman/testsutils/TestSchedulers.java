package com.github.mrvilkaman.testsutils;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import rx.Scheduler;
import rx.schedulers.TestScheduler;

/**
 * Created by Zahar on 19.03.16.
 */
public class TestSchedulers implements SchedulersProvider {

	private TestScheduler immediate = new TestScheduler();


	@Override
	public Scheduler mainThread() {
		return immediate;
	}

	@Override
	public Scheduler io() {
		return immediate;
	}

	@Override
	public Scheduler computation() {
		return immediate;
	}

	@Override
	public Scheduler immediate() {
		return immediate;
	}


	public TestScheduler getTestScheduler() {
		return immediate;
	}
}
