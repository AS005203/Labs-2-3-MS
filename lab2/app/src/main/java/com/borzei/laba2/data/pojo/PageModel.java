package com.borzei.laba2.data.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PageModel implements Serializable {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private ArrayList<DetailModel> movieModel;

    public PageModel(int page, int totalResults, int totalPages, ArrayList<DetailModel> movieModel) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.movieModel = movieModel;
    }

    public ArrayList<DetailModel> getMoviesList() {
        return movieModel;
    }

}