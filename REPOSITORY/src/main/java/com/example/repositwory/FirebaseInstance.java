package com.example.repositwory;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseInstance
{
    private static volatile FirebaseInstance _instance = null;
    public static FirebaseApp app;

    private FirebaseInstance(Context context)
    {
        FirebaseOptions options = new
                FirebaseOptions.Builder()
                .setProjectId("")		// ApplicationId
                .setApplicationId("")		// ProjectId
                .setApiKey("")
                .setStorageBucket("")
                .build();

        app = FirebaseApp.initializeApp(context, options);
    }

    public static FirebaseInstance instance(Context context)
    {
        if (_instance == null)
        {
            synchronized (FirebaseInstance.class)
            {
                if (_instance == null)
                    _instance = new FirebaseInstance(context);
            }
        }
        return _instance;
    }
}
