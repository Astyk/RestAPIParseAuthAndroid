package domain.services;

import dagger.Module;
import dagger.Provides;
import domain.api.Api;
import domain.api.ApiModule;

@Module(includes = ApiModule.class)
public class ServiceModule {
    @Provides Persistence providePersistence(){
        return new Persistence();
    }

    @Provides Session provideSession(Api api, Persistence persistence) {
        return new Session(api, persistence);
    }
}
