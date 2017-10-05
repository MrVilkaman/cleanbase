package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.di.ActivityCoreComponent
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment

class Frag1ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 1

    override fun nextFragment(): BaseFragment<*> {
        return Frag2ScreenFragment.open()
    }

    override fun daggerInject() {
        val component = getComponent(ActivityCoreComponent::class.java)
        DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this)
    }

    companion object {
        fun open(): Frag1ScreenFragment {
            return Frag1ScreenFragment()
        }
    }
}
