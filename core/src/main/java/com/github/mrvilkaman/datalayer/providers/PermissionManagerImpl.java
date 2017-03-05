package com.github.mrvilkaman.datalayer.providers;

import android.app.Activity;

import com.github.mrvilkaman.domainlayer.providers.PermissionManager;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;

import static com.github.mrvilkaman.domainlayer.providers.PermissionManager.AnswerPermission
		.ALLOWED;
import static com.github.mrvilkaman.domainlayer.providers.PermissionManager.AnswerPermission.DENIED;
import static com.github.mrvilkaman.domainlayer.providers.PermissionManager.AnswerPermission
		.NOT_ASK;

public class PermissionManagerImpl implements PermissionManager {

	private final RxPermissions rxPermissions;

	public PermissionManagerImpl(Activity activity) {
		rxPermissions = new RxPermissions(activity);
	}

	@Override
	public Observable<AnswerPermission> request(String... permissions) {
		return rxPermissions.requestEach(permissions).map(permission -> {
			if (permission.granted) {
				return ALLOWED;
			} else if (permission.shouldShowRequestPermissionRationale) {
				return NOT_ASK;
			} else {
				return DENIED;
			}
		});
	}
}
