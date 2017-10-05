package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.di.ActivityCoreComponent
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment

class Frag5ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 5

    override val icon: Int
        get() = android.R.drawable.ic_media_pause

    override fun nextFragment(): BaseFragment<*>? = null

    override fun daggerInject() {
        val component = getComponent(ActivityCoreComponent::class.java)
        DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this)
    }

    companion object {


        fun open(): Frag5ScreenFragment {
            return Frag5ScreenFragment()
        }
    }
}
