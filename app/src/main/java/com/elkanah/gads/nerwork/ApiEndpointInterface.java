package com.elkanah.gads.nerwork;

import com.elkanah.gads.models.Entity;
import com.elkanah.gads.models.LearningLeaders;
import com.elkanah.gads.models.SkillIQLeaders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiEndpointInterface {

  @GET("api/skilliq")
  Call<List<SkillIQLeaders>> getSkillIQ();

  @GET("api/hours")
  Call<List<LearningLeaders>> getLearningLeaders();
  @FormUrlEncoded

  @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
  Call<Entity> addLink(@Field("EmailAddress") String EmailAddress,
                       @Field("Name") String Name,
                       @Field("LastName") String LastName,
                       @Field("Link") String Link);

}
