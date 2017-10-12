package com.github.mrvilkaman.ui.screens.testfrags


import android.os.Bundle
import android.view.View
import com.github.mrvilkaman.R
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment
import kotlinx.android.synthetic.main.layout_fragscreen_fragment.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class FragBaseScreenFragment : BaseFragment<FragBasePresenter>() {

    @Inject lateinit open var router: Router

    protected open val icon: Int
        get() = R.drawable.ic_home

    protected abstract val number: Int

    override fun getLayoutId(): Int = R.layout.layout_fragscreen_fragment

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
        val text = Integer.toString(number)
        val toolbar = getToolbar()
        if (toolbar != null) {
            toolbar.show()
            toolbar.showIcon(icon) {
                getUiResolver().showToast(R.string.app_name)
            }
            toolbar.setTitle(text)
        }

        frag_text.text = text

        frag_stack.setOnClickListener { router.navigateTo(nextScreenKey()) }
        frag_no_stack.setOnClickListener { router.replaceScreen(nextScreenKey()) }
        frag_root.setOnClickListener { router.newRootScreen(nextScreenKey()) }
    }


    protected abstract fun nextScreenKey(): String?

}