package com.github.mrvilkaman.di


import android.support.v4.app.FragmentActivity
import com.github.mrvilkaman.core.R
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver
import com.github.mrvilkaman.ui.container.MainActivity
import com.github.mrvilkaman.ui.container.MainNavigator
import com.github.mrvilkaman.ui.screens.drawer.DrawerScreenFragment
import com.github.mrvilkaman.ui.screens.start.StartScreenFragment
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Navigator


@PerActivity
//@Component(dependencies = arrayOf(AppComponent::class),
//        modules = arrayOf(
//                CommonActivityModule::class,
//                ThrowableModule::class,
//                ActivityModule::class,
//                ToolbarEmptyModule::class,
////                ToolbarModule::class,
//                DrawerEmptyModule::class
////                DrawerModule::class
//        ))
interface ActivityComponent : ActivityCoreComponent, CommonComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(drawerScreenFragment: DrawerScreenFragment)

    fun inject(startScreenFragment: StartScreenFragment)
}

@Module
class ActivityModule {


    @Provides
    @PerActivity
    fun provideMainNavigator(
            activity: FragmentActivity,
            toolbarResolver: ToolbarResolver?,
            leftDrawerHelper: LeftDrawerHelper?

    ): Navigator = MainNavigator(activity, R.id.content, toolbarResolver,leftDrawerHelper)
}