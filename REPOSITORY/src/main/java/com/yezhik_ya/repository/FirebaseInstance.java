package com.yezhik_ya.repository;

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
                .setProjectId("blogs-622d4")
                .setApplicationId("1:973765315693:android:dd1f8deb583ef45c2e4364")
                .setApiKey("AIzaSyCy1e3M21nKsFtxCaXwSAFNUkG2HGEjsuI")
                .setStorageBucket("blogs-622d4.appspot.com")
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
                {
                    _instance = new FirebaseInstance(context);
                }
            }
        }

        return _instance;
    }
}
