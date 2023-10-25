package com.example.app_390.database;

import android.content.Context;
import android.content.SharedPreferences;

public class AppMemory {
    private SharedPreferences sp;
    public AppMemory(Context context)
    {
        sp = context.getSharedPreferences("main", Context.MODE_PRIVATE );
    }
}
