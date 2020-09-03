package com.elkanah.gads.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elkanah.gads.R;
import com.elkanah.gads.adapter.LearningLeadersAdapter;
import com.elkanah.gads.models.LearningLeaders;

import java.util.List;

public class PlaceholderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Observer<List<LearningLeaders>> {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LearningLeadersAdapter adapter;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        initRecycler(root);
        progressBar.setVisibility(View.VISIBLE);
        pageViewModel.gotoFetchData();
        loadData();
    }

    private void loadData() {
        pageViewModel.learningLeadersMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<LearningLeaders>>() {
            @Override
            public void onChanged(List<LearningLeaders> learningLeaders) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    adapter = new LearningLeadersAdapter(getContext(), learningLeaders);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initRecycler(View root) {
        swipeRefreshLayout = root.findViewById(R.id.swipe_leaders);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressBar = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.section_label);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onRefresh() {
        pageViewModel.gotoFetchData();
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onChanged(List<LearningLeaders> learningLeaders) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.setNewLeaders(learningLeaders);
        adapter.notifyDataSetChanged();
    }
}