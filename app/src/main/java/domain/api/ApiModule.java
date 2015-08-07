package domain.api;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class ApiModule {

    @Provides Endpoints provideEndPoints(){
        return  new RestAdapter.Builder()
                .setEndpoint(Config.URL_BASE)
                .build().create(Endpoints.class);
    }

    @Provides Api provideApi(Endpoints endpoints){
        return new Api(endpoints);
    }
}
