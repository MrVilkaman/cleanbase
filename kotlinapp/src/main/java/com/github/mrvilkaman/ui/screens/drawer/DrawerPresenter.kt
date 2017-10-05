package com.github.mrvilkaman.ui.screens.drawer


import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView
import ru.terrakok.cicerone.Router

import javax.inject.Inject

class DrawerPresenter @Inject
constructor(
        val router: Router

) : BasePresenter<BaseView>() {

    fun onClickMenu(screenKey:String){
        router.newRootScreen(screenKey)
    }

}