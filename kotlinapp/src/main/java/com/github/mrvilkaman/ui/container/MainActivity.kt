package com.github.mrvilkaman.ui.container

import android.support.v4.app.Fragment
import com.github.mrvilkaman.R
import com.github.mrvilkaman.di.ActivityComponent
import com.github.mrvilkaman.di.ActivityModule
import com.github.mrvilkaman.di.AppComponent
import com.github.mrvilkaman.di.DaggerActivityComponent
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule
import com.github.mrvilkaman.di.modules.activity.DrawerModule
import com.github.mrvilkaman.di.modules.activity.ToolbarModule
import com.github.mrvilkaman.presentationlayer.activities.BaseActivity
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper
import com.github.mrvilkaman.presentationlayer.utils.DevUtils
import com.github.mrvilkaman.ui.screens.drawer.DrawerScreenFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityComponent, BasePresenter<*>>() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var router: Router
    @Inject lateinit var leftDrawer: LeftDrawerHelper


    override fun injectMe(component: ActivityComponent) {
        component.inject(this)
    }

    override fun afterOnCreate() {

        supportFragmentManager.beginTransaction().add(leftDrawer.drawerContentFrame, leftDrawer.drawerFragment as Fragment).commit()

        val navigator1 = navigator
        if (navigator1 is MainNavigator) {
            navigator1.init()
        }
    }

    override fun createComponent(): ActivityComponent {
        val appComponent = DevUtils.getComponent(App.get(this), AppComponent::class.java)
        val commonActivityModule = CommonActivityModule(this, this, rootView)
        return DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .commonActivityModule(commonActivityModule)
                .activityModule(ActivityModule(this, containerID))
                .toolbarModule(ToolbarModule(this))
                .drawerModule(DrawerModule {
                    DrawerScreenFragment.open()
                })
                .build()
    }

    override fun getActivityLayoutResourceID(): Int = R.layout.cleanbase_activity_content_with_toolbar_and_drawer

    override fun onBackPressed() {
        router.exit()
//        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
