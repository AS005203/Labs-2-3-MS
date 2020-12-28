package com.borzei.laba2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borzei.laba2.R;
import com.borzei.laba2.data.RoomDatabase;
import com.borzei.laba2.data.Util;
import com.borzei.laba2.data.Repository;
import com.borzei.laba2.data.ScrollListener;
import com.borzei.laba2.data.pojo.DetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Repository repository;
    private ScrollListener scrollListener;
    private Button savedButton;
    private boolean isSavedMoviesShown = false;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        init(root);

        savedButton.setOnClickListener(v -> {
            if (isSavedMoviesShown) {
                isSavedMoviesShown = false;
                savedButton.setText("Show saved");
                getApiList();
            } else {
                isSavedMoviesShown = true;
                getDBList();
                savedButton.setText("Show all");
            }
        });

        scrollListener = new ScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                repository.getPopularMovies(page, new Util.MovieListLoadCallback() {
                    @Override
                    public void onLoadFail(Call call) {
                    }

                    @Override
                    public void onLoadSuccess(Response response, List<DetailModel> movieModels) {
                        adapter.addData(movieModels);
                    }
                });
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        return root;
    }

    void init(View view) {
        recyclerView = view.findViewById(R.id.movies_recycler_view);
        savedButton = view.findViewById(R.id.cached_button);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        repository = new Repository();
        repository.getPopularMovies(1, new Util.MovieListLoadCallback() {
            @Override
            public void onLoadFail(Call call) {
            }

            @Override
            public void onLoadSuccess(Response response, List<DetailModel> movieModels) {
                adapter.addData(movieModels);
            }
        });
    }

    private void getApiList() {
        repository.getPopularMovies(1, new Util.MovieListLoadCallback() {
            @Override
            public void onLoadFail(Call call) {
            }

            @Override
            public void onLoadSuccess(Response response, List<DetailModel> movieModels) {
                adapter.setData(movieModels);
            }
        });
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void getDBList() {
        adapter.setData(RoomDatabase.getDatabaseInstance(getContext()).moviesDAO().getAll());
        recyclerView.removeOnScrollListener(scrollListener);
        if (adapter.getItemCount() == 0 & Util.isInternetUnavailable(getActivity())) {
            Toast.makeText(getContext(), "No network and saved data", Toast.LENGTH_LONG).show();
        }
    }
}