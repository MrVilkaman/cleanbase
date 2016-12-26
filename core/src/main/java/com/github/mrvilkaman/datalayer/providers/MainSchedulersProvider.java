package com.github.mrvilkaman.datalayer.providers;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zahar on 22.01.2016.
 */
public class MainSchedulersProvider implements SchedulersProvider {

	@Override
	public Scheduler mainThread() {
		return AndroidSchedulers.mainThread();
	}

	@Override
	public Scheduler io() {
		return Schedulers.io();
	}

	@Override
	public Scheduler computation() {
		return Schedulers.computation();
	}

	@Override
	public Scheduler immediate() {
		return Schedulers.immediate();
	}
}
