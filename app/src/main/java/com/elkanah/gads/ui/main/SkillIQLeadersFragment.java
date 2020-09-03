package com.elkanah.gads.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elkanah.gads.R;
import com.elkanah.gads.adapter.SkillIQAdapter;
import com.elkanah.gads.models.SkillIQLeaders;

import java.util.List;


public class SkillIQLeadersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Observer<List<SkillIQLeaders>> {
    private SkillViewModel skillViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SkillIQAdapter adapter;

    public SkillIQLeadersFragment() {
        // Required empty public constructor
    }


    public static SkillIQLeadersFragment newInstance(String param1, String param2) {
        SkillIQLeadersFragment fragment = new SkillIQLeadersFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skillViewModel = new ViewModelProvider(this).get(SkillViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_skill_i_q_leaders, container, false);
        init(v);
        return v;
    }

    private void init(View root) {
        initRecycler(root);
        skillViewModel.gotoFetchData();
        loadData(root);
    }

    private void loadData(View root) {
        skillViewModel.learningLeadersMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<SkillIQLeaders>>() {
            @Override
            public void onChanged(List<SkillIQLeaders> learningLeaders) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    adapter = new SkillIQAdapter(getContext(), learningLeaders);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initRecycler(View root) {
        recyclerView = root.findViewById(R.id.skill_iq_leaders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = root.findViewById(R.id.swipe2);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressBar = root.findViewById(R.id.progressBar2);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onRefresh() {
        skillViewModel.gotoFetchData();
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onChanged(List<SkillIQLeaders> skillIQLeaders) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.setNewLeaders(skillIQLeaders);
        adapter.notifyDataSetChanged();
    }

}