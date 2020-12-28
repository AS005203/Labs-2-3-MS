package com.borzei.laba2.data;

import com.borzei.laba2.data.pojo.DetailModel;
import com.borzei.laba2.data.pojo.PageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET("movie/upcoming")
    Call<PageModel> getPopularMovies(@Query("page") int page, @Query("api_key") String userKey);

    @GET("movie/{movie_id}")
    Call<DetailModel> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String userKey);

}