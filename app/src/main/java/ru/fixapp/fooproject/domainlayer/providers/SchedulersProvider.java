package ru.fixapp.fooproject.domainlayer.providers;

import rx.Scheduler;

/**
 * Created by Zahar on 22.01.2016.
 */
public interface SchedulersProvider {

	Scheduler mainThread();

	Scheduler io();

	Scheduler computation();

	Scheduler immediate();
}
