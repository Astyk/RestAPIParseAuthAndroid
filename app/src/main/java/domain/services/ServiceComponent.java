package domain.services;

import javax.inject.Singleton;

import dagger.Component;
import ui.activities.BaseActivity;
import ui.fragments.BaseFragment;

@Singleton
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {
    Session provideSession();
    void injectClient(BaseActivity baseActivity);
    void injectClient(BaseFragment baseFragment);
}
