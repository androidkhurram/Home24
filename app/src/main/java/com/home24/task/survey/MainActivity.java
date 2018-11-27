package com.home24.task.survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.home24.task.survey.api.ApiManager;
import com.home24.task.survey.models.SurveyModel;
import com.home24.task.survey.utilities.Constants;
import com.home24.task.survey.utilities.Preferences;
import com.neberox.app.survey.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
{
    private EditText limitText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        limitText = (EditText) findViewById(R.id.countText);

        findViewById(R.id.startBtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String data = Preferences.getSharedPrefValue(MainActivity.this, Constants.USERDEFAULT_STORE_DATA);
                if (data == null)
                {
                    callAPI();
                }
                else
                {
                    ApiManager.getInstance().surveys = new Gson().fromJson(data, new TypeToken<ArrayList<SurveyModel>>(){}.getType());

                }
            }
        });
    }

    private void callAPI()
    {
        indicator.show();
        Call<ResponseBody> call = ApiManager.getInstance().mUrlManager.getSurveys(Constants.MAIN_BASE_URL + limitText.getText().toString());
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try
                {
                    ApiManager.getInstance().urlsList.clear();
                    JSONObject obj = new JSONObject(response.body().string());
                    obj = obj.getJSONObject("_links");
                    JSONArray array = obj.getJSONArray("articles");
                    for (int i = 0; i < array.length(); i++)
                    {
                        String url = array.getJSONObject(i).getString("href");
                        ApiManager.getInstance().urlsList.add(url);
                    }
                    callUrlApi();

                } catch (Exception e)
                {
                    indicator.dismiss();
                    Toast.makeText(MainActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                indicator.dismiss();
            }
        });
    }

    private void callUrlApi()
    {
        Call<ResponseBody> call = ApiManager.getInstance().mUrlManager.getSurveys(ApiManager.getInstance().urlsList.get(0));
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                indicator.dismiss();
                try
                {
                    ApiManager.getInstance().surveys.clear();
                    Preferences.saveIntegerSharedPrefValue(MainActivity.this, Constants.USERDEFAULT_REGISTER_COUNTER, 0);
                    String value = response.body().string();
                    SurveyModel survey = new Gson().fromJson(value, SurveyModel.class);
                    ApiManager.getInstance().saveSurvey(survey);
                    startActivity(new Intent(MainActivity.this, SurveyActivity.class));

                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                indicator.dismiss();
                Toast.makeText(MainActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
