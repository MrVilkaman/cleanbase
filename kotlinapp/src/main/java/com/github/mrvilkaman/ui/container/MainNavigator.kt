package com.github.mrvilkaman.ui.container

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.github.mrvilkaman.core.R
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver
import com.github.mrvilkaman.ui.screens.ScreenKey
import com.github.mrvilkaman.ui.screens.testfrags.*
import io.reactivex.Completable
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.SystemMessage
import java.util.concurrent.TimeUnit

class MainNavigator(
        private val activity: FragmentActivity,
        private val containerId: Int,
        private val toolbarResolver: ToolbarResolver?,
        private val leftDrawerHelper: LeftDrawerHelper?

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

    protected fun getMainScreenKey(): String? = ScreenKey.FRAG1


    //core part


    private var doubleBackToExitPressedOnce: Boolean = false
    override fun exit() {
        if (doubleBackToExitPressedOnce || !activity.isTaskRoot) {
            activity.finish()
        } else {
            Toast.makeText(activity, R.string.toast_exit, Toast.LENGTH_SHORT).show()

            doubleBackToExitPressedOnce = true
            Completable.complete().delay(1000, TimeUnit.MILLISECONDS)
                    .subscribe { doubleBackToExitPressedOnce = false }
        }
    }

    override fun applyCommand(command: Command?) {
        if (command !is SystemMessage) {
            toolbarResolver?.clear()
            leftDrawerHelper?.close()
        }

        if (command is Back) {
            if (1 < fragmentManager.backStackEntryCount) {
                super.applyCommand(command)
                val backStackEntryCount = fragmentManager.backStackEntryCount
                if (1 < backStackEntryCount) {
                    toolbarResolver?.showBackIcon()
                } else {
                    toolbarResolver?.showHomeIcon()
                }

            } else {
                exit()
            }
        } else {
            super.applyCommand(command)
            value.updateIcon()
        }
    }

    fun init() {
        if (fragmentManager.findFragmentById(containerId) == null) {
            val mainScreenKey = getMainScreenKey()
            if (mainScreenKey != null)
                applyCommand(Forward(mainScreenKey, null))
        }
        value.updateIcon()
        toolbarResolver?.setCallback(value)
    }

    private val value: ToolbarResolver.ToolbarResolverCallback = object : ToolbarResolver.ToolbarResolverCallback {
        override fun onClickHome() {
            if (fragmentManager.backStackEntryCount <= 1 && activity.isTaskRoot) {
                leftDrawerHelper?.open()
            } else {
                applyCommand(Back())
//                    activityView.hideKeyboard()
            }
        }

        override fun updateIcon() {
            if (fragmentManager.backStackEntryCount < 1 && activity.isTaskRoot) {
                toolbarResolver?.showHomeIcon()
            } else {
                toolbarResolver?.showBackIcon()
            }
        }

    }

}
