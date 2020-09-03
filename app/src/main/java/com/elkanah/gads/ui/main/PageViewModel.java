package com.elkanah.gads.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.gads.models.LearningLeaders;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends AndroidViewModel {
    public MutableLiveData<List<LearningLeaders>> learningLeadersMutableLiveData;
    private PageRepoitory mPRoxy;
    private List<LearningLeaders>  learningLeaders;
    public MutableLiveData<String> isDecision;
    public MutableLiveData<String> validate;


    public PageViewModel(@NonNull Application application) {
        super(application);
        learningLeadersMutableLiveData  = new MutableLiveData<>();
        mPRoxy = PageRepoitory.getInstance(application);
        learningLeaders = new ArrayList<>();
        isDecision = new MutableLiveData<>();
        validate = new MutableLiveData<>();
    }


    public void gotoFetchData() {
        mPRoxy.gotoFetchData(learningLeaders,learningLeadersMutableLiveData,isDecision);
    }

    public void validateInput(CharSequence firstName, CharSequence LastName, CharSequence email, CharSequence link) {
      mPRoxy.validateInput(firstName,LastName, email, link,validate);
    }
}