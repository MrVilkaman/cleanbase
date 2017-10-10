package com.github.mrvilkaman.di;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.domainlayer.providers.PermissionManager;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;


public interface ActivityCoreComponent extends CoreCommonComponent {

	UIResolver getUIResolver();

	ThrowableResolver getThrowableResolver();

	@Nullable IToolbar getIToolbar();

	PermissionManager providePermissionManager();
}