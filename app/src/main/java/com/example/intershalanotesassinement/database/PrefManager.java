package com.example.intershalanotesassinement.database;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static PrefManager instance;
    private SharedPreferences prefs;
    private Context context;

    public static PrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }

    public PrefManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
}
