package com.github.mrvilkaman.ui.screens.drawer

import android.os.Bundle
import android.view.View
import com.github.mrvilkaman.R
import com.github.mrvilkaman.di.ActivityComponent
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment
import com.github.mrvilkaman.presentationlayer.fragments.core.ISingletonFragment
import com.github.mrvilkaman.ui.screens.ScreenKey
import kotlinx.android.synthetic.main.drawer.*

class DrawerScreenFragment : BaseFragment<DrawerPresenter>(), ISingletonFragment {


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menu_1.setOnClickListener { presenter.onClickMenu(ScreenKey.FRAG1) }
        menu_2.setOnClickListener { presenter.onClickMenu(ScreenKey.FRAG2) }
        menu_3.setOnClickListener { presenter.onClickMenu(ScreenKey.FRAG3) }
        menu_4.setOnClickListener { presenter.onClickMenu(ScreenKey.FRAG4) }
        menu_5.setOnClickListener { presenter.onClickMenu(ScreenKey.FRAG5) }
    }

    override fun getLayoutId(): Int = R.layout.drawer


    override fun daggerInject() {
        val component = getComponent(ActivityComponent::class.java)
        component.inject(this)
    }

    companion object {

        fun open(): BaseFragment<*> {
            return DrawerScreenFragment()
        }
    }
}
