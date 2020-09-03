package com.elkanah.gads.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.gads.models.SkillIQLeaders;

import java.util.ArrayList;
import java.util.List;

public class SkillViewModel extends AndroidViewModel {
    public MutableLiveData<List<SkillIQLeaders>> learningLeadersMutableLiveData;
    private SkillRepository mPRoxy;
    private List<SkillIQLeaders>  learningLeaders;
    public MutableLiveData<String> isDecision;
    public SkillViewModel(@NonNull Application application) {
        super(application);
        learningLeadersMutableLiveData  = new MutableLiveData<>();
        mPRoxy = SkillRepository.getInstance(application);
        learningLeaders = new ArrayList<>();
        isDecision = new MutableLiveData<>();
    }

    public void gotoFetchData() {
        mPRoxy.gotoFetchData(learningLeaders,learningLeadersMutableLiveData,isDecision);
    }
}
