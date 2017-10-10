package com.github.mrvilkaman.di;


import android.content.Context;
import android.support.annotation.Nullable;

import com.github.mrvilkaman.dev.LeakCanaryProxy;
import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.utils.bus.Bus;


public interface CoreCommonComponent {

	SchedulersProvider getSchedulersProvider();

	GlobalSubscriptionManager getGlobalSubscriptionManager();

	Context provideContext();

	@Nullable
	LeakCanaryProxy provideLeakCanaryProxy();

	Bus provideBus();
}


