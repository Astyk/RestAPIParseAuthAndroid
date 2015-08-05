package domain.repository;

import com.google.gson.JsonObject;

import domain.models.User;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Api {
    private final Endpoints mEndpoints;

    public Api() {
        mEndpoints = new RestAdapter.Builder()
                .setEndpoint(Config.URL_BASE)
                .build().create(Endpoints.class);
    }

    public void login(String username, String password, final Callback<JsonObject> callback) {
        mEndpoints.login(username, password, handleCommonResponse(callback));
    }

    public void signUp(User user, final Callback<JsonObject> callback) {
        mEndpoints.singUp(user, handleCommonResponse(callback));
    }

    public void update(String token, String objectId, User user,
                       final Callback<JsonObject> callback) {
        mEndpoints.update(token, objectId, user, handleCommonResponse(callback));
    }

    public void currentSession(String token, final Callback<User> callback) {
        mEndpoints.currentSession(token, new retrofit.Callback<User>() {
            @Override public void success(User user, Response response) {
                callback.response(user);
            }

            @Override public void failure(RetrofitError error) {
                callback.response(null);
            }
        });
    }

    private retrofit.Callback<JsonObject> handleCommonResponse(final Callback<JsonObject> callback) {
        return new retrofit.Callback<JsonObject>() {
            @Override public void success(JsonObject jsonObject, Response response) {
                callback.response(jsonObject);
            }

            @Override public void failure(RetrofitError error) {
                callback.response((JsonObject) error.getBodyAs(JsonObject.class));
            }
        };
    }

    public interface Callback<T> {
        void response(T response);
    }
}
