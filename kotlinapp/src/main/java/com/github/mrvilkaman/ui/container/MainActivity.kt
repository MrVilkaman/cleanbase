package com.github.mrvilkaman.ui.container

import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.mrvilkaman.R
import com.github.mrvilkaman.presentationlayer.activities.BaseActivity
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelperImpl
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolverImpl
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject




class MainActivity : BaseActivity<BasePresenter<*>>(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =fragmentInjector

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var router: Router
    @Inject lateinit var leftDrawerHelper: LeftDrawerHelper
    @Inject lateinit var toolbar: ToolbarResolver


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
    }

    override fun afterOnCreate() {
        val leftDrawerHelper1 = leftDrawerHelper
        if (leftDrawerHelper1 is LeftDrawerHelperImpl) {
            leftDrawerHelper1.init(rootView)
        }

        val toolbar1 = toolbar
        if (toolbar1 is ToolbarResolverImpl) {
            toolbar1.init(rootView,this)
        }

        val navigator1 = navigator
        if (navigator1 is MainNavigator) {
            navigator1.init()
        }

    }

//    override
//    fun createComponent(): ActivityComponent {
//        val appComponent = DevUtils.getComponent(App.get(this), AppComponent::class.java)
//        return DaggerActivityComponent.builder()
//                .appComponent(appComponent)
//                .commonActivityModule(CommonActivityModule(this, rootView))
//                .activityModule(ActivityModule(this, containerID))
//                .toolbarModule(ToolbarModule(this))
//                .drawerModule(DrawerModule(this) {
//                    DrawerScreenFragment.open()
//                })
//                .build()
//    }

    override fun getActivityLayoutResourceID(): Int = R.layout.cleanbase_activity_content_with_toolbar_and_drawer

    override fun onBackPressed() {
        router.exit()
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
