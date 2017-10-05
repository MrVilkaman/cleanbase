package com.github.mrvilkaman.ui.container

import com.github.mrvilkaman.R
import com.github.mrvilkaman.di.ActivityComponent
import com.github.mrvilkaman.di.ActivityModule
import com.github.mrvilkaman.di.AppComponent
import com.github.mrvilkaman.di.DaggerActivityComponent
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule
import com.github.mrvilkaman.di.modules.activity.FragmentModule
import com.github.mrvilkaman.presentationlayer.activities.BaseActivity
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter
import com.github.mrvilkaman.presentationlayer.resolution.ProvideFragmentCallback
import com.github.mrvilkaman.presentationlayer.utils.DevUtils
import com.github.mrvilkaman.ui.screens.testfrags.Frag1ScreenFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityComponent, BasePresenter<*>>() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var router: Router


    override fun injectMe(component: ActivityComponent) {
        component.inject(this)
    }

    override fun afterOnCreate() {
    }

    override fun createComponent(): ActivityComponent {
        val appComponent = DevUtils.getComponent(App.get(this), AppComponent::class.java)
        val commonActivityModule = CommonActivityModule(this, this, rootView, ProvideFragmentCallback { Frag1ScreenFragment.open() })
        return DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .commonActivityModule(commonActivityModule)
                .fragmentModule(FragmentModule(supportFragmentManager, containerID))
                .activityModule(ActivityModule(this,containerID))
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
