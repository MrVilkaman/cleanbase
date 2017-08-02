package com.github.mrvilkaman.domainlayer.providers;


import io.reactivex.Observable;

public interface PermissionManager {

	Observable<AnswerPermission> request(String... permissions);

	enum AnswerPermission {
		ALLOWED,
		DENIED,
		NOT_ASK
	}
}
