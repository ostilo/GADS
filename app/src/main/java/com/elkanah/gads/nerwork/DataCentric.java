package com.elkanah.gads.nerwork;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.gads.models.Entity;
import com.elkanah.gads.models.LearningLeaders;
import com.elkanah.gads.models.SkillIQLeaders;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataCentric {
    public static DataCentric ourInstance;
    public static Gson gson;


    public static DataCentric getInstance(Application application) {
        if (ourInstance == null) {
            gson = new Gson();
            ourInstance = new DataCentric();
        }
        return ourInstance;
    }

    public void gotoFetchData(final List<LearningLeaders> learningLeaders, final MutableLiveData<List<LearningLeaders>> learningLeadersMutableLiveData, final MutableLiveData<String> isDecision) {
        ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        final Gson gson = new Gson();
        Call<List<LearningLeaders>> callItem = apiService.getLearningLeaders();
        callItem.enqueue(new Callback<List<LearningLeaders>>() {
            @Override
            public void onResponse(Call<List<LearningLeaders>> call, Response<List<LearningLeaders>> response) {
                if(response.body() != null){
                    learningLeadersMutableLiveData.postValue(response.body());
                    isDecision.postValue("s");
                } else if (response.body() == null) {
                    isDecision.postValue("f");
                }
            }

            @Override
            public void onFailure(Call<List<LearningLeaders>> call, Throwable t) {
                isDecision.postValue("f");
            }
        });

    }


    public void gotoFetchDataSkill(List<SkillIQLeaders> learningLeaders, final MutableLiveData<List<SkillIQLeaders>> learningLeadersMutableLiveData, final MutableLiveData<String> isDecision) {
        ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        final Gson gson = new Gson();
        Call<List<SkillIQLeaders>> callItem = apiService.getSkillIQ();
        callItem.enqueue(new Callback<List<SkillIQLeaders>>() {
            @Override
            public void onResponse(Call<List<SkillIQLeaders>> call, Response<List<SkillIQLeaders>> response) {
                if(response.body() != null){
                    learningLeadersMutableLiveData.postValue(response.body());
                    isDecision.postValue("s");
                } else if (response.body() == null) {
                    isDecision.postValue("f");
                }
            }
            @Override
            public void onFailure(Call<List<SkillIQLeaders>> call, Throwable t) {
                isDecision.postValue("f");
            }
        });
    }

    public void gotoSubmitData(Entity rn, final MutableLiveData<String> isDecision) {
        ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
            Call<Entity> call = apiService.addLink(rn.EmailAddress,rn.Name,rn.LastName,rn.Link);
            call.enqueue(new Callback<Entity>() {
                @Override
                public void onResponse(@NonNull Call<Entity> call, @NonNull Response<Entity> response) {
                    if (response.code() == 200)
                    {
                        if(response.message().equals("OK")){
                            isDecision.postValue(response.message());
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Entity> call, @NonNull Throwable t) {
                    isDecision.postValue(t.getMessage());
                }
            });

        }
}
