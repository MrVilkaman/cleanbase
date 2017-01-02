package com.github.mrvilkaman.dev;

import android.support.annotation.NonNull;

public interface LeakCanaryProxy {
	void init();

	void watch(@NonNull Object object);
}
