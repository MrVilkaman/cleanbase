package com.github.mrvilkaman.domainlayer.providers;


import rx.Scheduler;

public interface SchedulersProvider {

	Scheduler mainThread();

	Scheduler io();

	Scheduler computation();

	Scheduler immediate();
}
