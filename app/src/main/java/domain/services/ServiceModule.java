package domain.services;

import dagger.Module;
import dagger.Provides;
import domain.repository.Api;

@Module
public class ServiceModule {
    @Provides Persistence providePersistence(){
        return new Persistence();
    }
    @Provides Api provideApi(){
        return new Api();
    }
}
