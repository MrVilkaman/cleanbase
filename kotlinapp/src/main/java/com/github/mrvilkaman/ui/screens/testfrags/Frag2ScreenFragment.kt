package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.di.ActivityCoreComponent
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment

class Frag2ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 2

    override val icon: Int
        get() = android.R.drawable.ic_menu_add

    override fun nextFragment(): BaseFragment<*> {
        return Frag3ScreenFragment.open()
    }

    override fun daggerInject() {
        val component = getComponent(ActivityCoreComponent::class.java)
        DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this)
    }

    companion object {


        fun open(): Frag2ScreenFragment {
            return Frag2ScreenFragment()
        }
    }
}
