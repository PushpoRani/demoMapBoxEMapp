package com.pushpo.demobarikoiemapp.Utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

/**
 * Created by metakave-android-PC on 16-Oct-18.
 */

public class ApiClient {
    private static final String BASE_URL = "http://barikoi.xyz/v1/api/";
    private static Retrofit retrofit = null;
    private static String CLIENT_KEY;
    private static String CLIENT_SCERET;
    private static final String TAG = "ApiClient";

    public static Retrofit getApiClient() {

        Log.d(TAG, "retroit is predefined null");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        CLIENT_KEY = SharedPreferenceManager.getStringFromSharePreference(MyApplication.getAppContext(), AppConstant.CLIENT_KEY);
//        CLIENT_SCERET = SharedPreferenceManager.getStringFromSharePreference(MyApplication.getAppContext(), AppConstant.CLIENT_SECRET);

//        Log.d(TAG, "CLIENT_KEY: " + CLIENT_KEY);
//        Log.d(TAG, "CLIENT_SCERET: " + CLIENT_SCERET);

//        if (CLIENT_KEY != null && CLIENT_SCERET != null) {

        //OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CLIENT_KEY, CLIENT_SCERET);
        // consumer.setTokenWithSecret(token, tokenSecret);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
//                    .addInterceptor(new SigningInterceptor(consumer))
                .addInterceptor(interceptor)
                .build();


        if (retrofit == null) {
            Log.d(TAG, "retroit is  null");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        } else {
            Log.d(TAG, "retroit is not null: " + retrofit.toString());
        }
//        } else {
//            Log.d(TAG, "CLIENT_KEY & CLIENT_SCERET is null");
//        }


        return retrofit;
    }
}
