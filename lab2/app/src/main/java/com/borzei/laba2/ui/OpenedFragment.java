package com.borzei.laba2.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.borzei.laba2.R;
import com.borzei.laba2.data.RoomDatabase;
import com.borzei.laba2.data.Util;
import com.borzei.laba2.data.Repository;
import com.borzei.laba2.data.pojo.DetailModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import retrofit2.Call;
import retrofit2.Response;

public class OpenedFragment extends Fragment {

    private TextView title;
    private TextView tag;
    private TextView description;
    private TextView budget;
    private TextView revenue;
    private TextView release;
    private ImageView poster;
    private Repository repository = new Repository();
    private int id;
    private DetailModel movieDetail;
    private Button save;

    public OpenedFragment() {

    }

    public static OpenedFragment newInstance(int param1) {
        OpenedFragment fragment = new OpenedFragment();
        Bundle args = new Bundle();
        args.putInt("id", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_details, container, false);
        init(root);

        save.setOnClickListener(v -> {
            if (movieDetail != null) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        RoomDatabase.getDatabaseInstance(getContext()).moviesDAO().insert(movieDetail);
                        return null;
                    }
                }.execute();
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });


        if (Util.isInternetUnavailable(getContext())) {
            setDetails(RoomDatabase.getDatabaseInstance(getContext()).moviesDAO().getById(id));
        } else {
            repository.getMovieDetails(id, new Util.MovieDetailsLoadCallback() {
                @Override
                public void onLoadFail(Call call) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoadSuccess(Response response, DetailModel detailModel) {
                    movieDetail = detailModel;
                    setDetails(detailModel);
                }
            });
        }
        return root;
    }

    private void init(View view) {
        title = view.findViewById(R.id.movie_details_title);
        tag = view.findViewById(R.id.movie_details_tag);
        description = view.findViewById(R.id.movie_details_description);
        poster = view.findViewById(R.id.movie_details_poster);
        budget = view.findViewById(R.id.movie_details_budget);
        revenue = view.findViewById(R.id.movie_details_revenue);
        release = view.findViewById(R.id.movie_details_release);
        save = view.findViewById(R.id.movie_details_favorite_button);
    }

    public void setDetails(final DetailModel details) {
        title.setVisibility(View.VISIBLE);
        title.setText(details.getTitle());
        tag.setVisibility(View.VISIBLE);
        tag.setText(details.getTagline());
        description.setVisibility(View.VISIBLE);
        description.setText(details.getOverview());
        poster.setVisibility(View.VISIBLE);
        budget.setText("Budget - " + details.getBudget() + " $");
        revenue.setText("Revenue - " + details.getRevenue() + " $");
        release.setText("Release date: " + details.getReleaseDate());

        Glide.with(this)
                .load(Util.IMG_BIG_SIZE_URL.concat(details.getPosterPath()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(poster);
    }
}