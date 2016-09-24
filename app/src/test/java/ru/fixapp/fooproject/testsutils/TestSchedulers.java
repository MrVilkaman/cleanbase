package ru.fixapp.fooproject.testsutils;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Zahar on 19.03.16.
 */
public class TestSchedulers implements SchedulersProvider {
	@Override
	public Scheduler mainThread() {
		return Schedulers.immediate();
	}

	@Override
	public Scheduler io() {
		return Schedulers.immediate();
	}

	@Override
	public Scheduler computation() {
		return Schedulers.immediate();
	}

	@Override
	public Scheduler immediate() {
		return Schedulers.immediate();
	}
}
