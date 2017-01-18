package com.github.mrvilkaman.di;

import com.github.mrvilkaman.domainlayer.interactor.LongpullingInteractor;

public interface CommonComponent {

	LongpullingInteractor getLongpullingInteractor();
}
