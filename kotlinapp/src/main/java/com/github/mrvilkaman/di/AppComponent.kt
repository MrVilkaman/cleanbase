package com.github.mrvilkaman.di


import com.github.mrvilkaman.di.modules.*
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Component(modules = arrayOf(
        //@formatter:off
		AppModule::class,
		DevModule::class,
		NetworkModule::class,
		EventBusModule::class,
		CoreProvidersModule::class,
        NavigationModule::class,
		ImageLoaderModule::class))
 //@formatter:on
@Singleton
interface AppComponent : AppCoreComponent, CommonComponent


interface CommonComponent {
    fun provideRouter(): Router
    fun provideNavigatorHolder(): NavigatorHolder
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