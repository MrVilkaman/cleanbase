package ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop;

import dagger.Component;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityCoreComponent;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class)
public interface CropImageScreenComponent {
	void inject(CropImageFragment fragment);

}