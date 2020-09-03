package com.elkanah.gads.ui.main;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.elkanah.gads.models.Entity;
import com.elkanah.gads.models.LearningLeaders;
import com.elkanah.gads.nerwork.DataCentric;

import java.util.List;

public class PageRepoitory {
    public static PageRepoitory pageRepoitory;
    public static DataCentric mProxy;

    public static PageRepoitory getInstance(Application application)
    {
        if(pageRepoitory == null)
        {
            pageRepoitory = new PageRepoitory();
            mProxy = DataCentric.getInstance(application);
        }
        return pageRepoitory;
    }

    public void gotoFetchData(List<LearningLeaders> learningLeaders, MutableLiveData<List<LearningLeaders>> learningLeadersMutableLiveData, MutableLiveData<String> isDecision) {
        mProxy.gotoFetchData(learningLeaders,learningLeadersMutableLiveData,isDecision);
    }

    public void validateInput(CharSequence text, CharSequence text1, CharSequence text2, CharSequence text3, MutableLiveData<String> isDecision) {
      if(!TextUtils.isEmpty(text.toString()) && !TextUtils.isEmpty(text1.toString()) && !TextUtils.isEmpty(text2.toString()) && !TextUtils.isEmpty(text3.toString()))
      {
          Entity rn = new Entity(text2.toString(),text.toString(),text1.toString(),text3.toString());
          mProxy.gotoSubmitData(rn, isDecision);
          isDecision.postValue("su");
      }
      else {
          isDecision.postValue("f");
      }
    }
}
