package com.github.mrvilkaman.ui.container

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.github.mrvilkaman.presentationlayer.resolution.BaseNavigator
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver
import com.github.mrvilkaman.ui.screens.ScreenKey
import com.github.mrvilkaman.ui.screens.drawer.DrawerScreenFragment
import com.github.mrvilkaman.ui.screens.testfrags.*


class MainNavigator(
        activity: AppCompatActivity,
        containerId: Int,
        toolbarResolver: ToolbarResolver?,
        leftDrawerHelper: LeftDrawerHelper?

) : BaseNavigator(
        activity,
        containerId,
        toolbarResolver,
        leftDrawerHelper
) {
    override fun getDrawer(): Fragment? =
        DrawerScreenFragment.open()



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

    override fun getMainScreenKey(): String? = ScreenKey.FRAG1

}
