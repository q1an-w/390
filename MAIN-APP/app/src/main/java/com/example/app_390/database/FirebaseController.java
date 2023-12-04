package com.example.app_390.database;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.TextView;

import com.example.app_390.data.DataLayout;
import com.example.app_390.home.HomeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class FirebaseController {

    private FirebaseFirestore db;
    public FirebaseController(){
        db =FirebaseFirestore.getInstance();
    }

    public void addUser(String username, String pwd,String device_id,MyActivityCallback cb, boolean validInputs){
        if(!validInputs){
            cb.activityCallback(HomeLayout.class, false, true, "Invalid Inputs");
            return;
        }

        CollectionReference colRef= db.collection("DrainFlow_Users");
        Map<String,Object> newUser = new HashMap<>();
        newUser.put("username",username);
        newUser.put("password",pwd);
        newUser.put("private_device_ID",device_id);
        newUser.put("email","");
            colRef.whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if(task.getResult().isEmpty()){
                            colRef.whereEqualTo("private_device_ID",device_id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if(task.getResult().isEmpty()){
                                            cb.activityCallback(HomeLayout.class, true,true,"Success");
                                            colRef.document("profile-"+username).set(newUser);
                                        }else cb.activityCallback(HomeLayout.class, false,true,"Device ID Already Exists");

                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());

                                    }
                                }
                            });
                        }else cb.activityCallback(HomeLayout.class, false,true,"Username Already Exists");

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());

                    }
                }
            });

    }
    public void deleteOldUser(String username){
        db.collection("DrainFlow_Users").document("profile-"+ username)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, username + " DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
    public void loadAuthData(String username, String pwd,AppMemory appMemory, MyAuthCallback callback) {
        DocumentReference docRef;
        docRef= db.collection("DrainFlow_Users").document("profile-"+username);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    boolean auth = false;
                    String input = "";
                    try{
                       input = doc.getData().get("password").toString();
                       appMemory.saveSignup(doc.getData().get("username").toString(),doc.getData().get("password").toString(),doc.getData().get("private_device_ID").toString());
                    }catch(Exception e){
                        callback.authCallback(false);
                        return;
                    }

                    if(input.equals(pwd)){
                        auth = true;
                    }else;
                    callback.authCallback(auth);
                } else {
                    callback.authCallback(false);
                    System.out.println("Auth Exception");
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    public void getNewestData(AppMemory appMemory, TextView flow, TextView level, WaveProgressBar levelFlowIndicator, MyDataCallback cb){
        db.collection(appMemory.getSavedLoginDeviceID() + "_data").orderBy("time", Query.Direction.DESCENDING).limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        cb.resetDataList();

                        for (QueryDocumentSnapshot doc : value) {
                            Log.d(TAG, doc.getId() + " => " + doc.getData().get("time"));
                            String[] dataex = new String[7];
                            Timestamp timestamp = (Timestamp) doc.getData().get("time");

                            //if check timestamp = a while ago,
                            dataex[0] = timestamp.toDate().toString() ;
                            dataex[1] = " ";
                            dataex[2] = doc.getData().get("level").toString();;
                            dataex[3] = doc.getData().get("rate").toString();
                            dataex[4] = doc.getData().get("level_state").toString();
                            dataex[5] = doc.getData().get("rate_state").toString();
                            dataex[6] = doc.getData().get("connection").toString();
                            cb.dataCallback(flow, level, levelFlowIndicator,HomeLayout.class,dataex);
                        }
                        Log.d(TAG, "Current ");
                    }
                });
    }


    public void getData(AppMemory appMemory, MyDataCallback cb){

        db.collection(appMemory.getSavedLoginDeviceID() + "_data").orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        cb.resetDataList();
                        String prevLevel = "init", prevRate = "init";

                        for (QueryDocumentSnapshot doc : value) {
                            String[] dataex = new String[6];
                            Timestamp timestamp = (Timestamp) doc.getData().get("time");
                            String level = doc.getData().get("level").toString();
                            String rate = doc.getData().get("rate").toString();
                            String level_state = doc.getData().get("level_state").toString();
                            String rate_state= doc.getData().get("rate_state").toString();
                            dataex[0] = timestamp.toDate().toString() ;
                            dataex[1] = " ";
                            dataex[2] = level;
                            dataex[3] = rate;
                            dataex[4] = level_state;
                            dataex[5] = rate_state;
                            if(prevLevel.matches(level) && prevRate.matches(rate) ) {
                                cb.dataCallback(DataLayout.class,dataex,true);
                            }else{
                                cb.dataCallback(DataLayout.class,dataex,false);
                            }
                            prevLevel = level;
                            prevRate = rate;
                            //if check timestamp = a while ago,

                        }
                        Log.d(TAG, "Current ");
                    }
                });
    }

    public void savePreferences(boolean enableEmail, boolean enableWeather, boolean enableVoice, AppMemory appMemory) {
       db.collection("DrainFlow_Users").document("profile-"+appMemory.getSavedLoginUsername())
                .update("enableEmail", enableEmail, "enableWeather", enableWeather, "enableVoice",enableVoice)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        appMemory.savePreferences(enableEmail,enableWeather,enableVoice);
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public void loadPreferences(MyPreferencesCallback myPreferencesCallback, AppMemory appMemory) {
        DocumentReference docRef;
        docRef= db.collection("DrainFlow_Users").document("profile-"+appMemory.getSavedLoginUsername());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    boolean enableEmail,enableWeather,enableVoice;
                    enableEmail = (boolean) doc.getData().get("enableEmail");
                    enableWeather = (boolean) doc.getData().get("enableWeather");
                    enableVoice = (boolean) doc.getData().get("enableVoice");


                    myPreferencesCallback.savePrefs(enableEmail,enableWeather,enableVoice );
                } else {

                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void getNotifications(AppMemory appMemory, MyNotificationsCallback cb) {
        db.collection(appMemory.getSavedLoginDeviceID() + "_data").orderBy("time", Query.Direction.DESCENDING).limit(5)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        if(value.isEmpty()){
                            return;
                        }
                        ArrayList<String[]>data = new ArrayList<String[]>();

                        for (QueryDocumentSnapshot doc : value) {
                            Log.d(TAG, doc.getId() + " => " + doc.getData().get("time"));
                            String[] individualdata = new String[6];
                            Timestamp timestamp = (Timestamp) doc.getData().get("time");

                            String[] date_time = formatDate(timestamp.toDate().toString());


                            individualdata[1] = date_time[0];
                            individualdata[2] = date_time[1];
                            individualdata[0] = doc.getData().get("level").toString();
                            individualdata[3] = doc.getData().get("rate").toString();
                            individualdata[4] = doc.getData().get("level_state").toString();
                            individualdata[5] = doc.getData().get("rate_state").toString();


                            data.add(individualdata);
                        }
                        while (data.size() < 5) {
                            String[] nullData = new String[4];
                            Arrays.fill(nullData, null); // Fill array with null values
                            data.add(nullData);
                        }
                        cb.notifCallback(data);
                        Log.d(TAG, data.toString());
                    }
                });
    }
    private String[] formatDate(String date_string) {
        String[] date_time = new String[2];
        SimpleDateFormat initialformatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");
        try {
            Date basedate = initialformatter.parse(date_string);
            date_time[0] = dateformatter.format(basedate);
            date_time[1] = timeformatter.format(basedate);
        }
        catch (ParseException e){

        }
        return date_time;
    }

    public void updateEmail(AppMemory appMemory, String email) {
        db.collection("DrainFlow_Users").document("profile-"+appMemory.getSavedLoginUsername())
                .update("email", email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }



    //functions for getting, setting, authentication




}



