package com.home24.task.survey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.home24.task.survey.api.ApiManager;
import com.home24.task.survey.models.SurveyModel;
import com.home24.task.survey.utilities.Constants;
import com.home24.task.survey.utilities.Preferences;
import com.neberox.app.survey.R;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyListActivity extends BaseActivity
{
    private int currentCounter = 0;
    private SurveyModel selectedSurvey = null;

    private CardView reviewBtn;
    private ImageView likeBtn;
    private ImageView unlikeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        currentCounter = Preferences.getIntSharedPrefValue(this, Constants.USERDEFAULT_REGISTER_COUNTER, 0);
        selectedSurvey = ApiManager.getInstance().surveys.get(currentCounter);

        unlikeBtn = (ImageView) findViewById(R.id.unLikeBtn);
        likeBtn   = (ImageView) findViewById(R.id.likeBtn);

        reviewBtn   = (CardView) findViewById(R.id.reviewBtn);
        reviewBtn.setEnabled(false);
        reviewBtn.setAlpha(0.6f);

        setupValues();
    }

    private void setupValues()
    {
        likeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedSurvey.isLiked = true;
                ApiManager.getInstance().saveSurvey(selectedSurvey);

            }
        });

        unlikeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

    private void callAPI()
    {
        Call<ResponseBody> call = ApiManager.getInstance().mUrlManager.getSurveys(Constants.MAIN_BASE_URL);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try
                {
                    JSONObject obj = new JSONObject(response.body().string());
                    obj = obj.getJSONObject("_links");
                    ArrayList<String> urls = new Gson().fromJson(obj.getString("articles"), new TypeToken<ArrayList<String>>(){}.getType());
                    ApiManager.getInstance().urlsList.addAll(urls);
                    startActivity(new Intent(SurveyListActivity.this, SurveyListActivity.class));

                } catch (Exception e)
                {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }

    private void callUrlApi()
    {
        Call<ResponseBody> call = ApiManager.getInstance().mUrlManager.getSurveys(ApiManager.getInstance().urlsList.get(currentCounter));
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try
                {
                    SurveyModel survey = new Gson().fromJson(response.body().string(), SurveyModel.class);
                    ApiManager.getInstance().saveSurvey(survey);
                    startActivity(new Intent(SurveyListActivity.this, SurveyListActivity.class));

                } catch (Exception e)
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }
}
