package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.di.ActivityComponent

class Frag5ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 5

    override val icon: Int
        get() = android.R.drawable.ic_media_pause

    override fun nextScreenKey(): String? = null

    override fun daggerInject() {
        val component = getComponent(ActivityComponent::class.java)
        DaggerFragScreenComponent.builder().activityComponent(component).build().inject(this)
    }

    companion object {

        fun open(): Frag5ScreenFragment {
            return Frag5ScreenFragment()
        }
    }
}
