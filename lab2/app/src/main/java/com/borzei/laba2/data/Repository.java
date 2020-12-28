package com.borzei.laba2.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.borzei.laba2.data.pojo.DetailModel;
import com.borzei.laba2.data.pojo.PageModel;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    RetrofitApi api = new Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
            .build().create(RetrofitApi.class);

    public void getPopularMovies(int page, Util.MovieListLoadCallback callback) {
        new PopularMoviesTask(page, callback).execute();
    }

    private class PopularMoviesTask extends AsyncTask<Void, Void, Void> {

        private int mPage;
        private Util.MovieListLoadCallback mCallback;

        PopularMoviesTask(int page, Util.MovieListLoadCallback callback) {
            mPage = page;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            api.getPopularMovies(mPage, Util.API_KEY).enqueue(new Callback<PageModel>() {
                @Override
                public void onResponse(@NonNull Call<PageModel> call, @NonNull Response<PageModel> response) {
                    mCallback.onLoadSuccess(response, response.body().getMoviesList());
                }

                @Override
                public void onFailure(@NonNull Call<PageModel> call, @NonNull Throwable t) {
                    mCallback.onLoadFail(call);
                }
            });
            return null;
        }
    }

    public void getMovieDetails(int id, Util.MovieDetailsLoadCallback callback) {
        new MovieDetailsTask(id, callback).execute();
    }

    private class MovieDetailsTask extends AsyncTask<Void, Void, Void> {

        private int mId;
        private Util.MovieDetailsLoadCallback mCallback;

        MovieDetailsTask(int id, Util.MovieDetailsLoadCallback callback) {
            mId = id;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            api.getMovieDetails(mId, Util.API_KEY).enqueue(new Callback<DetailModel>() {
                @Override
                public void onResponse(@NonNull Call<DetailModel> call, @NonNull Response<DetailModel> response) {
                    mCallback.onLoadSuccess(response, response.body());
                }

                @Override
                public void onFailure(@NonNull Call<DetailModel> call, @NonNull Throwable t) {
                    mCallback.onLoadFail(call);
                }
            });
            return null;
        }
    }
}