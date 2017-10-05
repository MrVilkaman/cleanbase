package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView

import javax.inject.Inject

class FragBasePresenter @Inject
constructor() : BasePresenter<BaseView>() {

    override fun onViewAttached() {}
}
