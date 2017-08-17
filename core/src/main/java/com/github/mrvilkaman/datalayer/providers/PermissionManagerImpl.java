package com.github.mrvilkaman.datalayer.providers;

import android.app.Activity;

import com.github.mrvilkaman.domainlayer.providers.PermissionManager;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

import static com.github.mrvilkaman.domainlayer.providers.PermissionManager.AnswerPermission.ALLOWED;
import static com.github.mrvilkaman.domainlayer.providers.PermissionManager.AnswerPermission.DENIED;
import static com.github.mrvilkaman.domainlayer.providers.PermissionManager.AnswerPermission.NOT_ASK;

public class PermissionManagerImpl implements PermissionManager {

	private final RxPermissions rxPermissions;

	public PermissionManagerImpl(Activity activity) {
		rxPermissions = new RxPermissions(activity);
	}

	@Override
	public Observable<AnswerPermission> request(String... permissions) {
		return rxPermissions.requestEach(permissions)
				.map(new Function<Permission, AnswerPermission>() {
					@Override
					public AnswerPermission apply(@NonNull Permission permission) throws Exception {
						if (permission.granted) {
							return ALLOWED;
						} else if (permission.shouldShowRequestPermissionRationale) {
							return NOT_ASK;
						} else {
							return DENIED;
						}
					}
				});
	}
}
