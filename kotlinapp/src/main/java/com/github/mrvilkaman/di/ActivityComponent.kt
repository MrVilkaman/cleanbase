package com.github.mrvilkaman.di


import android.support.v4.app.FragmentActivity
import com.github.mrvilkaman.di.modules.activity.*
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver
import com.github.mrvilkaman.ui.container.MainActivity
import com.github.mrvilkaman.ui.container.MainNavigator
import com.github.mrvilkaman.ui.screens.drawer.DrawerScreenFragment
import com.github.mrvilkaman.ui.screens.start.StartScreenFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Navigator


@PerActivity
@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(
                CommonActivityModule::class,
                ThrowableModule::class,
                FragmentModule::class,
                ActivityModule::class,
                ToolbarModule::class,
                DrawerModule::class))
interface ActivityComponent : ActivityCoreComponent, CommonComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(drawerScreenFragment: DrawerScreenFragment)

    fun inject(startScreenFragment: StartScreenFragment)
}

@Module
class ActivityModule(
        private val activity: FragmentActivity,
        private val containerId: Int) {


    @Provides
    @PerActivity
    fun provideMainNavigator(
            toolbarResolver: ToolbarResolver?
    ): Navigator = MainNavigator(activity, containerId, toolbarResolver)
}