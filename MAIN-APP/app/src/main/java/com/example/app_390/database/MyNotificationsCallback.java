package com.example.app_390.database;

import java.util.ArrayList;

public interface MyNotificationsCallback {
    void notifCallback(ArrayList<String[]> data);

    void resetDataList();
}
