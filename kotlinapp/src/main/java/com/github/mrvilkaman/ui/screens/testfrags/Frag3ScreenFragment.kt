package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.di.ActivityCoreComponent
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment

class Frag3ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 3

    override val icon: Int
        get() = android.R.drawable.ic_delete

    override fun nextFragment(): BaseFragment<*> {
        return Frag4ScreenFragment.open()
    }

    override fun daggerInject() {
        val component = getComponent(ActivityCoreComponent::class.java)
        DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this)
    }

    companion object {


        fun open(): Frag3ScreenFragment {
            return Frag3ScreenFragment()
        }
    }
}
