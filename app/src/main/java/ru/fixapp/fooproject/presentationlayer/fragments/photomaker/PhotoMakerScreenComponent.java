package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.photoulits.PhotoHelper;
import ru.fixapp.fooproject.presentationlayer.photoulits.PhotoHelperImpl;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {PhotoMakerScreenComponent.PhotoMakerScreenModule.class})
public interface PhotoMakerScreenComponent {
	void inject(PhotoMakerScreenFragment fragment);


	@Module
	class PhotoMakerScreenModule {

		private BaseFragment baseFragment;

		public PhotoMakerScreenModule(BaseFragment baseFragment) {
			this.baseFragment = baseFragment;
		}

		@PerScreen
		@Provides
		public PhotoHelper getPhotoHelper(){
			return new PhotoHelperImpl(baseFragment);
		}
	}
}
