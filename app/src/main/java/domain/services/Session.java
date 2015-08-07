package domain.services;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import javax.inject.Inject;

import domain.models.User;
import domain.api.Api;
import edu.victoralbertos.restapiparseauthandroid.R;

public class Session {
    private final Api mApi;
    private final Persistence mPersistence;

    @Inject public Session(Api api, Persistence persistence) {
        mApi = api;
        mPersistence = persistence;
    }

    public void login(final Context context, String username, String password, final Callback<String> callback) {
        mApi.login(username, password, handleCommonResponse(context, callback));
    }

    public void signUp(final Context context, String email, String username, String phone, String password, final Callback<String> callback) {
        User user = new User(username, email, phone, password);
        mApi.signUp(user, handleCommonResponse(context, callback));
    }

    public void update(final Context context, String email, String username, String phone, final Callback<String> callback) {
        String token = mPersistence.retrieveToken(context);
        String objectId = mPersistence.retrieveObjectId(context);

        User user = new User(username, email, phone);
        mApi.update(token, objectId, user, handleCommonResponse(context, callback));
    }

    private Api.Callback<JsonObject> handleCommonResponse(final Context context, final Callback<String> callback) {
        return new Api.Callback<JsonObject>() {
            @Override public void response(JsonObject response) {
                if (response == null) {
                    callback.response(false, context.getString(R.string.failure));
                    return;
                }

                if (response.has("error")) {
                    String message = response.get("error").getAsString();
                    callback.response(false, message);
                    return;
                }

                if (response.has("sessionToken")) {
                    String token = response.get("sessionToken").getAsString();
                    String objectId = response.get("objectId").getAsString();
                    mPersistence.saveCredentials(context, objectId, token);
                }

                String successMessage = context.getString(R.string.success);
                callback.response(true, successMessage);
            }
        };
    }

    @Nullable public void currentSession(final Context context, final Callback<User> callback) {
        String token = mPersistence.retrieveToken(context);
        mApi.currentSession(token, new Api.Callback<User>() {
            @Override public void response(User user) {
                callback.response(user!=null, user);
            }
        });
    }

    public boolean isActive(Context context) {
        return !mPersistence.retrieveObjectId(context).isEmpty();
    }

    public void destroy(Context context) {
        mPersistence.removeCredentials(context);
    }

    public interface Callback<T> {
        void response(boolean success, T feedback);
    }
}
