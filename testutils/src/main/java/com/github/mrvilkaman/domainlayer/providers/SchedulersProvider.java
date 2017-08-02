package com.github.mrvilkaman.domainlayer.providers;


import io.reactivex.Scheduler;

public interface SchedulersProvider {

	Scheduler mainThread();

	Scheduler io();

	Scheduler computation();

	Scheduler immediate();
}
