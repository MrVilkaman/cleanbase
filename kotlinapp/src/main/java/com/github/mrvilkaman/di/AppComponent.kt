package com.github.mrvilkaman.di


import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.github.mrvilkaman.di.modules.CoreProvidersModule
import com.github.mrvilkaman.di.modules.DevModule
import com.github.mrvilkaman.di.modules.EventBusModule
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule
import com.github.mrvilkaman.di.modules.activity.DrawerModule
import com.github.mrvilkaman.di.modules.activity.ThrowableModule
import com.github.mrvilkaman.di.modules.activity.ToolbarModule
import com.github.mrvilkaman.ui.container.App
import com.github.mrvilkaman.ui.container.MainActivity
import dagger.*
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton


@Component(modules = arrayOf(
        //@formatter:off
		MyAppModule::class,
		DevModule::class,
		EventBusModule::class,
		CoreProvidersModule::class,
        NavigationModule::class))
 //@formatter:on
@Singleton
interface MyAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): MyAppComponent
    }

    fun inject(app: App)
}


@Module(includes = arrayOf(AndroidSupportInjectionModule::class))
interface MyAppModule {

    @Binds
    @PerActivity
    fun provideFeatureView(featureActivity: MainActivity): AppCompatActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(
            CommonActivityModule::class,
            ThrowableModule::class,
            ActivityModule::class,
            ActivityFragModule::class,
            ToolbarModule::class,
            DrawerModule::class
    ))
    fun mainActivityInjector(): MainActivity
}

@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(): Router = cicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder
}