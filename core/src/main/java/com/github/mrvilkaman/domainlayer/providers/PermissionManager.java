package com.github.mrvilkaman.domainlayer.providers;


import rx.Observable;

public interface PermissionManager {

	Observable<AnswerPermission> request(String... permissions);

	enum AnswerPermission {
		ALLOWED,
		DENIED,
		NOT_ASK
	}
}
