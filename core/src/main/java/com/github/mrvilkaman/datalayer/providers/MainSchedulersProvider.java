package com.github.mrvilkaman.datalayer.providers;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
		return Schedulers.trampoline();
	}
}
