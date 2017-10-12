package com.github.mrvilkaman.ui.container

import com.github.mrvilkaman.R
import com.github.mrvilkaman.presentationlayer.activities.BaseActivity
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter




class MainActivity : BaseActivity<BasePresenter<*>>() {


    override fun afterOnCreate() {
    }

    override fun getActivityLayoutResourceID(): Int = R.layout.cleanbase_activity_content_with_toolbar_and_drawer

}
