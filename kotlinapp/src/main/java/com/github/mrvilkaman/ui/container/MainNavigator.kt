package com.github.mrvilkaman.ui.container


import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.github.mrvilkaman.ui.screens.ScreenKey
import com.github.mrvilkaman.ui.screens.testfrags.*
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command

class MainNavigator(activity: FragmentActivity, containerId: Int) : SupportAppNavigator(activity, containerId) {

    private val fragmentManager = activity.supportFragmentManager

    override fun createActivityIntent(screenKey: String, data: Any?): Intent? {
        return null
    }

    override fun createFragment(screenKey: String, data: Any?): Fragment? = when (screenKey) {
        ScreenKey.FRAG1 -> Frag1ScreenFragment.open()
        ScreenKey.FRAG2 -> Frag2ScreenFragment.open()
        ScreenKey.FRAG3 -> Frag3ScreenFragment.open()
        ScreenKey.FRAG4 -> Frag4ScreenFragment.open()
        ScreenKey.FRAG5 -> Frag5ScreenFragment.open()
        else ->
            null
    }

    override fun applyCommand(command: Command?) {
        if (command is Back) {

            if (fragmentManager.backStackEntryCount > 1) {
                super.applyCommand(command)
            } else {
                super.exit()
            }
        } else {
            super.applyCommand(command)
        }
    }
}
