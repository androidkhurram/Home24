package com.home24.task.survey;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{
    public MyApplication app;
    public ProgressDialog indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();

        indicator = new ProgressDialog(this);
        indicator.setTitle("Please wait...");
    }

    @Override
    public void finish()
    {
        super.finish();
    }
}
