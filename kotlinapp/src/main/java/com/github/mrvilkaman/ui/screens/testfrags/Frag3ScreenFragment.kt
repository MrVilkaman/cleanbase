package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.R
import com.github.mrvilkaman.di.ActivityComponent
import com.github.mrvilkaman.ui.screens.ScreenKey

class Frag3ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 3

    override val icon: Int
        get() = android.R.drawable.ic_delete

    override fun nextScreenKey(): String? = ScreenKey.FRAG4

    override fun daggerInject() {
        val component = getComponent(ActivityComponent::class.java)
        DaggerFragScreenComponent.builder().activityComponent(component).build().inject(this)
    }

    override fun onBackPressed(): Boolean {
        uiResolver.showToast(R.string.cleanbase_simple_text,"QWETR")
        return super.onBackPressed()
    }

    companion object {


        fun open(): Frag3ScreenFragment {
            return Frag3ScreenFragment()
        }
    }
}
