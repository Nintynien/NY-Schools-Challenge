package com.example.newyorkschools.util;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.newyorkschools.data.SATScores;
import com.example.newyorkschools.data.School;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NycSchoolApi {
    private static final String BASE_URL = "https://data.cityofnewyork.us";
    public interface NycSchoolApiEndpoint {
        @GET("resource/s3k6-pzi2.json")
        Call<List<School>> getSchools();
        @GET("resource/f9bf-2cp4.json")
        Call<List<SATScores>> getSatScores();
    }

    public static void getSchools(MutableLiveData<List<School>> schools) {
        Log.i(NycSchoolApi.class.getName(), "Getting schools");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.cityofnewyork.us")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NycSchoolApiEndpoint service = retrofit.create(NycSchoolApiEndpoint.class);
        Call<List<School>> movies = service.getSchools();
        movies.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                List<School> data = response.body();
                if (data != null) {
                    Log.i(NycSchoolApi.class.getName(), "Got " + data.size() + " schools");
                    schools.postValue(data);
                }
            }
            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
                Log.e(NycSchoolApi.class.getName(), "Error getting schools", t);
            }
        });
    }

    public static void getSatScores(MutableLiveData<List<SATScores>> scores) {
        Log.i(NycSchoolApi.class.getName(), "Getting SAT scores");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NycSchoolApiEndpoint service = retrofit.create(NycSchoolApiEndpoint.class);
        Call<List<SATScores>> movies = service.getSatScores();
        movies.enqueue(new Callback<List<SATScores>>() {
            @Override
            public void onResponse(Call<List<SATScores>> call, Response<List<SATScores>> response) {
                List<SATScores> data = response.body();
                if (data != null) {
                    Log.i(NycSchoolApi.class.getName(), "Got " + data.size() + " SAT scores");
                    scores.postValue(data);
                }
            }
            @Override
            public void onFailure(Call<List<SATScores>> call, Throwable t) {
                Log.e(NycSchoolApi.class.getName(), "Error getting SAT scores", t);
            }
        });
    }
}
