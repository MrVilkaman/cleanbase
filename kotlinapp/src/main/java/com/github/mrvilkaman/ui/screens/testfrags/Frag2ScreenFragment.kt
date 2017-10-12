package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.ui.screens.ScreenKey

class Frag2ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 2

    override val icon: Int
        get() = android.R.drawable.ic_menu_add

    override fun nextScreenKey(): String? = ScreenKey.FRAG3

    companion object {
        fun open(): Frag2ScreenFragment {
            return Frag2ScreenFragment()
        }
    }
}
