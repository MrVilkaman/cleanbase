package com.github.mrvilkaman.ui.screens.testfrags


import android.os.Bundle
import android.view.View
import com.github.mrvilkaman.R
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment
import kotlinx.android.synthetic.main.layout_fragscreen_fragment.*

abstract class FragBaseScreenFragment : BaseFragment<FragBasePresenter>() {


    protected open val icon: Int
        get() = R.drawable.ic_home

    protected abstract val number: Int

    override fun getLayoutId(): Int = R.layout.layout_fragscreen_fragment

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
//        val toolbar = getToolbar()
//        toolbar!!.show()
        //		toolbar.showIcon(getIcon(),() -> getUiResolver().showToast(R.string.app_name));
        val text = Integer.toString(number)
//        toolbar.setTitle(text)
        frag_text.text = text

        frag_stack.setOnClickListener { navigation.showFragment(nextFragment()) }
        frag_no_stack.setOnClickListener { navigation.showFragmentWithoutBackStack(nextFragment()) }
        frag_root.setOnClickListener { navigation.showRootFragment(nextFragment()) }
    }

    protected abstract fun nextFragment(): BaseFragment<*>?

}