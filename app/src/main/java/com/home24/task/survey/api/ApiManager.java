package com.home24.task.survey.api;

import android.location.Location;

import com.home24.task.survey.models.SurveyModel;
import com.home24.task.survey.utilities.Constants;
import com.home24.task.survey.MyApplication;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Muhammad on 21/03/2015.
 */
public class ApiManager
{
    public ThreadPoolExecutor executor;

    private static ApiManager mInstance;

    public static synchronized ApiManager getInstance()
    {
        return mInstance;
    }

    private MyApplication mContext;

    public static Retrofit mApiAdapter;
    public static URLManager  mUrlManager;

    public ArrayList<SurveyModel> surveys = new ArrayList<SurveyModel>();
    public ArrayList<String> urlsList = new ArrayList<String>();


    public Location mLocation = new Location("");

    public static void clear()
    {
        if (mInstance != null)
        {
            mInstance = null;
        }
    }

    private okhttp3.OkHttpClient getClient()
    {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build();

        return client;
    }

    public ApiManager(MyApplication mContext)
    {
        this.mContext = mContext;
        mInstance = this;

        mApiAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        System.setProperty("https.keepAlive", "false");
        mUrlManager = mApiAdapter.create(URLManager.class);

        executor = new ThreadPoolExecutor(
                5,
                10,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );

        executor.allowCoreThreadTimeOut(true);
    }

    public void saveSurvey(SurveyModel survey)
    {
        surveys.add(survey);
    }
}

