package com.github.mrvilkaman.ui.screens.testfrags

import com.github.mrvilkaman.di.ActivityCoreComponent
import com.github.mrvilkaman.di.PerScreen

import dagger.Component
import dagger.Module

@PerScreen
@Component(dependencies = arrayOf(ActivityCoreComponent::class),
        modules = arrayOf(FragScreenComponent.FragScreenModule::class))
interface FragScreenComponent {
    fun inject(fragment: Frag1ScreenFragment)
    fun inject(fragment: Frag2ScreenFragment)
    fun inject(fragment: Frag3ScreenFragment)
    fun inject(fragment: Frag4ScreenFragment)
    fun inject(fragment: Frag5ScreenFragment)

    @Module
    class FragScreenModule
}