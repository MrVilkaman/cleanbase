package com.github.mrvilkaman.ui.screens.drawer

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.github.mrvilkaman.R
import com.github.mrvilkaman.di.ActivityComponent
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment
import com.github.mrvilkaman.presentationlayer.fragments.core.ISingletonFragment

class DrawerScreenFragment : BaseFragment<DrawerPresenter>(), ISingletonFragment {

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int = R.layout.drawer

    @OnClick(R.id.menu_1)
    internal fun onClick1() {
        //		getNavigation().showRootFragment(Frag1ScreenFragment.open());
    }

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
