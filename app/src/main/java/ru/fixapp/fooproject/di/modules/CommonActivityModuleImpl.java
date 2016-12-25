package ru.fixapp.fooproject.di.modules;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.fixapp.fooproject.di.modules.activity.CommonActivityModule;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.testfrags.Frag1ScreenFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.fragments.FragmentResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.navigation.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.navigation.NavigationResolverImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.ToolbarResolver;

public class CommonActivityModuleImpl extends CommonActivityModule {
	public CommonActivityModuleImpl(AppCompatActivity activity, BaseActivityView baseActivityView,
									View view, FragmentManager fm, int contentId) {
		super(activity, baseActivityView, view, fm, contentId);
	}

	@Override
	public NavigationResolver createNavigationResolver(FragmentResolver fragmentResolver,
													   LeftDrawerHelper drawer,
													   ToolbarResolver toolbarResolver,
													   UIResolver ui) {
		//// TODO: 25.12.16 libcore fix!
		return new NavigationResolverImpl(activity, fragmentResolver, drawer, toolbarResolver, ui,
				baseActivityView){
			@Override
			public BaseFragment createStartFragment() {
				return Frag1ScreenFragment.open();
			}
		};
	}


}
