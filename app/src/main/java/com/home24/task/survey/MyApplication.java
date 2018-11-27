package com.home24.task.survey;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.home24.task.survey.api.ApiManager;
import com.home24.task.survey.utilities.Constants;
import com.neberox.app.survey.R;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
        mailTo = "khurram.naqi123@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        forceCloseDialogAfterToast = false, // optional, default false
        resToastText = R.string.app_name
)
public class MyApplication extends Application
{
    public ApiManager apiManager;

    @Override
    public void onCreate()
    {
        super.onCreate();

        MultiDex.install(this);
        Fresco.initialize(this);
        apiManager = new ApiManager(this);

        if (Constants.isDebug)
            ACRA.init(this);
    }
}