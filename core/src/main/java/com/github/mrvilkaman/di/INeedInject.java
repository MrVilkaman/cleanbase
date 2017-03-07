package com.github.mrvilkaman.di;


public interface INeedInject<C> {
	void injectMe(C component);
}
