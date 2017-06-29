package com.github.mrvilkaman.datalayer.provider;


import rx.Observable;

public interface CacheworkDp {
	Observable<String> observeString();

	void refreshString();
}
