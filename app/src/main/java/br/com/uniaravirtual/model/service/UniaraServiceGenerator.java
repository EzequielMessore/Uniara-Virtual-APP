package br.com.uniaravirtual.model.service;

import com.squareup.okhttp.OkHttpClient;

import br.com.uniaravirtual.model.enums.SharedPreferencesValues;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class UniaraServiceGenerator {

    public static final String API_BASE_URL = "https://uniara-virtual-api.herokuapp.com";

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(API_BASE_URL)
            .setClient(new OkClient(new OkHttpClient()));

    public static <T> T createService(Class<T> serviceClass) {
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", SharedPreferencesValues.TOKEN.getString());
                request.addHeader("Accept", "application/json");
            }
        });
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
