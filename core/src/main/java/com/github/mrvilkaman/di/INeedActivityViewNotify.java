package com.github.mrvilkaman.di;



public interface INeedActivityViewNotify {
	int INIT_PRIORITY_FIRST = 1;
	int INIT_PRIORITY_SECOND = 2;
	int INIT_PRIORITY_THIRD = 3;
	int INIT_PRIORITY_NO = Integer.MAX_VALUE;

	void onInit();
}
