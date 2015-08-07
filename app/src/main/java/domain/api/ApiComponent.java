package domain.api;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    Api provideApi();
}
