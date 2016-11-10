package ru.fixapp.fooproject.presentationlayer.resolution.fragments;


import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.ToolbarResolver;

public class MyFragmentResolverCallback implements FragmentResolver.FragmentResolverCallback {
	private final ToolbarResolver toolbarResolver;

	public MyFragmentResolverCallback(ToolbarResolver toolbarResolver) {
		this.toolbarResolver = toolbarResolver;
	}

	@Override
	public void onRootFragment() {
		toolbarResolver.showHomeIcon();
	}

	@Override
	public void onNotRootFragment() {
		toolbarResolver.showBackIcon();
	}
}