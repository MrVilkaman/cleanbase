package com.github.mrvilkaman.domainlayer.providers;


import io.reactivex.Scheduler;

/**
 * Created by Zahar on 22.01.2016.
 */
public interface SchedulersProvider {

	Scheduler mainThread();

	Scheduler io();

	Scheduler computation();

	Scheduler immediate();
}
