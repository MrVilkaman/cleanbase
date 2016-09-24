package ru.fixapp.fooproject.presentationlayer.toolbar;


import android.support.v4.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;

public class ToolbarMenuHelper {

	private final Runnable refrashRun;
	ArrayMap<Integer, Runnable> runnableMap = new ArrayMap<>();

	public ToolbarMenuHelper(Runnable refrashRun) {
		this.refrashRun = refrashRun;
	}

	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		for (Integer integer : runnableMap.keySet()) {
			MenuItem add = menu.add(Menu.NONE, integer, Menu.NONE, "");
			add.setIcon(integer);
			add.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
	}

	public void onOptionsItemSelected(MenuItem item) {
		runnableMap.get(item.getItemId())
				.run();
	}

	public void showIcon(int res, Runnable callback) {
		runnableMap.put(res, callback);
		refrashRun.run();
	}

	public void clear() {
		runnableMap.clear();
		refrashRun.run();
	}

	public void remove(int resId) {
		runnableMap.remove(resId);
		refrashRun.run();
	}
}
