package com.github.mrvilkaman.ui.screens.testfrags

class Frag5ScreenFragment : FragBaseScreenFragment() {

    override val number: Int
        get() = 5

    override val icon: Int
        get() = android.R.drawable.ic_media_pause

    override fun nextScreenKey(): String? = null

    companion object {

        fun open(): Frag5ScreenFragment {
            return Frag5ScreenFragment()
        }
    }
}
