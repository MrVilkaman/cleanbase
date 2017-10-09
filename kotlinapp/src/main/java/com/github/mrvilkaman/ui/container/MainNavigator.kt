package com.github.mrvilkaman.ui.container


import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver
import com.github.mrvilkaman.ui.screens.ScreenKey
import com.github.mrvilkaman.ui.screens.testfrags.*
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.SystemMessage

class MainNavigator(
        activity: FragmentActivity,
        private val containerId: Int,
        private val toolbarResolver: ToolbarResolver?

) : SupportAppNavigator(activity, containerId) {

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

    protected fun getMainScreenKey(): String = ScreenKey.FRAG1


    //core part
    override fun applyCommand(command: Command?) {
        if (command !is SystemMessage) {
            toolbarResolver?.clear()
        }

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

    fun init(){
        if (fragmentManager.findFragmentById(containerId) == null) {
            applyCommand(Forward(getMainScreenKey(),null))
        }
    }



}
