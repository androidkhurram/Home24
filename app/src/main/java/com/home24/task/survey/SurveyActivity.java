package com.home24.task.survey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.home24.task.survey.api.ApiManager;
import com.home24.task.survey.models.SurveyModel;
import com.home24.task.survey.utilities.Constants;
import com.home24.task.survey.utilities.Preferences;
import com.neberox.app.survey.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends BaseActivity
{
    private int currentCounter = 0;
    private SurveyModel selectedSurvey = null;

    private CardView reviewBtn;
    private ImageView likeBtn;
    private ImageView unlikeBtn;

    private TextView description;
    private SimpleDraweeView mainImage;

    private TextView counterText;

    @Override
    public void onBackPressed()
    {
        int counter = currentCounter - 1;
        finish();
        if(counter >= 1)
        {
            Preferences.saveIntegerSharedPrefValue(SurveyActivity.this, Constants.USERDEFAULT_REGISTER_COUNTER, counter);
            startActivity(new Intent(SurveyActivity.this, SurveyActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        counterText = (TextView) findViewById(R.id.counterText);

        currentCounter = Preferences.getIntSharedPrefValue(this, Constants.USERDEFAULT_REGISTER_COUNTER, 0);
        selectedSurvey = ApiManager.getInstance().surveys.get(currentCounter);

        int likesCounter = 0;
        for (int i = 0; i < ApiManager.getInstance().surveys.size(); i++)
        {
            if (ApiManager.getInstance().surveys.get(i).isLiked)
                likesCounter = likesCounter + 1;
        }

        counterText.setText("Liked\n" + String.valueOf(likesCounter) + "/" + ApiManager.getInstance().urlsList.size());

        description = (TextView) findViewById(R.id.description);
        description.setText(Html.fromHtml(selectedSurvey.description));

        mainImage = (SimpleDraweeView) findViewById(R.id.mainImage);
        if (selectedSurvey.media.size() > 0)
            mainImage.setImageURI(selectedSurvey.media.get(0).uri);

        unlikeBtn = (ImageView) findViewById(R.id.unLikeBtn);
        likeBtn   = (ImageView) findViewById(R.id.likeBtn);

        reviewBtn   = (CardView) findViewById(R.id.reviewBtn);
        reviewBtn.setEnabled(false);
        reviewBtn.setAlpha(0.6f);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int counter = currentCounter - 1;
                finish();
                if(counter >= 0)
                {
                    Preferences.saveIntegerSharedPrefValue(SurveyActivity.this, Constants.USERDEFAULT_REGISTER_COUNTER, counter);
                    startActivity(new Intent(SurveyActivity.this, SurveyActivity.class));
                }
            }
        });

        setupValues();
    }

    private void setupValues()
    {
        likeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ApiManager.getInstance().surveys.get(currentCounter).isLiked = true;
                currentCounter++;
                if (ApiManager.getInstance().urlsList.size() == currentCounter)
                {
                    reviewBtn.setEnabled(true);
                    reviewBtn.setAlpha(1.0f);
                    description.setText("No Articles are left. Please review");
                    description.setGravity(Gravity.CENTER);
                    likeBtn.setVisibility(View.GONE);
                    unlikeBtn.setVisibility(View.GONE);

                    mainImage.setImageResource(R.drawable.blue_tick);

                    int likesCounter = 0;
                    for (int i = 0; i < ApiManager.getInstance().surveys.size(); i++)
                    {
                        if (ApiManager.getInstance().surveys.get(i).isLiked)
                            likesCounter = likesCounter + 1;
                    }

                    counterText.setText("Liked\n" + String.valueOf(likesCounter) + "/" + ApiManager.getInstance().urlsList.size());

                }
                else
                    callUrlApi();
            }
        });

        unlikeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ApiManager.getInstance().surveys.get(currentCounter).isLiked = false;
                currentCounter++;
                if (ApiManager.getInstance().urlsList.size() == currentCounter)
                {
                    reviewBtn.setEnabled(true);
                    reviewBtn.setAlpha(1.0f);
                    description.setText("No Articles are left. Please review");
                    description.setGravity(Gravity.CENTER);
                    likeBtn.setVisibility(View.GONE);
                    unlikeBtn.setVisibility(View.GONE);
                    mainImage.setImageResource(R.drawable.blue_tick);

                    int likesCounter = 0;
                    for (int i = 0; i < ApiManager.getInstance().surveys.size(); i++)
                    {
                        if (ApiManager.getInstance().surveys.get(i).isLiked)
                            likesCounter = likesCounter + 1;
                    }

                    counterText.setText("Liked\n" + String.valueOf(likesCounter) + "/" + ApiManager.getInstance().urlsList.size());
                }
                else
                    callUrlApi();
            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SurveyActivity.this, ResultActivity.class));
            }
        });
    }

    private void callUrlApi()
    {
        indicator.show();
        Call<ResponseBody> call = ApiManager.getInstance().mUrlManager.getSurveys(ApiManager.getInstance().urlsList.get(currentCounter));
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                indicator.dismiss();
                try
                {
                    Preferences.saveIntegerSharedPrefValue(SurveyActivity.this, Constants.USERDEFAULT_REGISTER_COUNTER, currentCounter);
                    SurveyModel survey = new Gson().fromJson(response.body().string(), SurveyModel.class);
                    ApiManager.getInstance().saveSurvey(survey);
                    finish();
                    startActivity(new Intent(SurveyActivity.this, SurveyActivity.class));

                } catch (Exception e)
                {
                    currentCounter--;
                    Toast.makeText(SurveyActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                indicator.dismiss();
                Toast.makeText(SurveyActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                currentCounter--;
            }
        });
    }
}
