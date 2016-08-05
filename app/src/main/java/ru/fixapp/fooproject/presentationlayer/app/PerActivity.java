package ru.fixapp.fooproject.presentationlayer.app;

import java.lang.annotation.Retention;

import javax.inject.Scope;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * Created by Zahar on 24.03.16.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
