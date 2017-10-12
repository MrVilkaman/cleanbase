package com.github.mrvilkaman.di


import android.support.v7.app.AppCompatActivity
import com.github.mrvilkaman.core.R
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver
import com.github.mrvilkaman.ui.container.MainNavigator
import com.github.mrvilkaman.ui.screens.drawer.DrawerScreenFragment
import com.github.mrvilkaman.ui.screens.testfrags.*
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import ru.terrakok.cicerone.Navigator


@Module
class ActivityModule {


    @Provides
    @PerActivity
    fun provideMainNavigator(
            activity: AppCompatActivity,
            toolbarResolver: ToolbarResolver?,
            leftDrawerHelper: LeftDrawerHelper?

    ): Navigator = MainNavigator(activity, R.id.content, toolbarResolver, leftDrawerHelper)

    @Provides
    @IntoMap
    @IntKey(Int.MAX_VALUE)
    @PerActivity
    fun createLeftDrawerHelperINeedActivityViewNotify(helper: Navigator): INeedActivityViewNotify =
            helper as INeedActivityViewNotify
}


@Module
interface ActivityFragModule {

    @PerScreen
    @ContributesAndroidInjector
    abstract fun getDrawerScreenFragment(): DrawerScreenFragment

    @PerScreen
    @ContributesAndroidInjector
    abstract fun getFrag1ScreenFragment(): Frag1ScreenFragment

    @PerScreen
    @ContributesAndroidInjector
    abstract fun getFrag2ScreenFragment(): Frag2ScreenFragment

    @PerScreen
    @ContributesAndroidInjector
    abstract fun getFrag3ScreenFragment(): Frag3ScreenFragment

    @PerScreen
    @ContributesAndroidInjector
    abstract fun getFrag4ScreenFragment(): Frag4ScreenFragment

    @PerScreen
    @ContributesAndroidInjector
    abstract fun getFrag5ScreenFragment(): Frag5ScreenFragment
}

