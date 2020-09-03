package com.elkanah.gads.ui.main;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.elkanah.gads.models.SkillIQLeaders;
import com.elkanah.gads.nerwork.DataCentric;

import java.util.List;

public class SkillRepository {
    public static SkillRepository pageRepoitory;
    public static DataCentric mProxy;
    public static SkillRepository getInstance(Application application)
    {
        if(pageRepoitory == null)
        {
            pageRepoitory = new SkillRepository();
            mProxy = DataCentric.getInstance(application);
        }
        return pageRepoitory;
    }

    public void gotoFetchData(List<SkillIQLeaders> learningLeaders, MutableLiveData<List<SkillIQLeaders>> learningLeadersMutableLiveData, MutableLiveData<String> isDecision) {
        mProxy.gotoFetchDataSkill(learningLeaders,learningLeadersMutableLiveData,isDecision);
    }
}
