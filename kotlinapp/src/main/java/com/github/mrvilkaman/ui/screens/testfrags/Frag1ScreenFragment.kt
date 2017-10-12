package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.ui.screens.ScreenKey

class Frag1ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 1

    override fun nextScreenKey(): String? = ScreenKey.FRAG2


    companion object {
        fun open(): Frag1ScreenFragment {
            return Frag1ScreenFragment()
        }
    }
}
