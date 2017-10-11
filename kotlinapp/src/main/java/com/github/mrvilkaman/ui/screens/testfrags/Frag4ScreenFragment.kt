package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.ui.screens.ScreenKey

class Frag4ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 4

    override val icon: Int
        get() = android.R.drawable.ic_dialog_map

    override fun nextScreenKey(): String? = ScreenKey.FRAG5


    override fun daggerInject() {
//        val component = getComponent(ActivityComponent::class.java)
//        DaggerFragScreenComponent.builder().activityComponent(component).build().inject(this)
    }

    companion object {


        fun open(): Frag4ScreenFragment {
            return Frag4ScreenFragment()
        }
    }
}
