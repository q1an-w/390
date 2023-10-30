package com.example.app_390.database;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.app_390.data.DataLayout;
import com.example.app_390.database.models.FormattedData;
import com.example.app_390.database.models.User;
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
import com.google.type.Date;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FirebaseController {

    private FirebaseFirestore db;
    public FirebaseController(){
        db =FirebaseFirestore.getInstance();
    }
    public void testAddUser(User user){
        //test
        Map<String, FormattedData> data = new HashMap<>();
        for(Integer i = 0; i <10;i++){
            data.put(i.toString(),new FormattedData(true));
        }


        //test end
        db.collection("390users").document("profile-"+user.getUsername()).set(user);
        db.collection("390data").document("data-"+user.getUsername()+user.getPrivate_device_ID()).set(data);
    }
    public void addUser(String username, String pwd,String device_id,MyActivityCallback cb, boolean validInputs){
        if(!validInputs){
            cb.activityCallback(HomeLayout.class, false, true, "Invalid Inputs");
            return;
        }

        CollectionReference colRef= db.collection("390users");
        Map<String,Object> newUser = new HashMap<>();
        newUser.put("username",username);
        newUser.put("password",pwd);
        newUser.put("private_device_ID",device_id);
            colRef.whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if(task.getResult().isEmpty()){
                            cb.activityCallback(HomeLayout.class, true,true,"Success");
                            colRef.document("profile-"+username).set(newUser);
                        }else cb.activityCallback(HomeLayout.class, false,true,"Username Already Exists");

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());

                    }
                }
            });
    }
    public void deleteOldUser(String username){
        db.collection("390users").document("profile-"+ username)
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
        docRef= db.collection("390users").document("profile-"+username);

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
    public void getData(MyDataCallback cb){
        CollectionReference colRef;
        colRef= db.collection("RPIdata");
        db.collection("RPIdata").orderBy("time", Query.Direction.DESCENDING)
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
                            String[] dataex = new String[4];
                            Timestamp timestamp = (Timestamp) doc.getData().get("time");
                            dataex[0] = timestamp.toDate().toString() ;
                            dataex[1] = " ";
                            dataex[2] = " 5 ";
                            dataex[3] = doc.getData().get("rate").toString();
                            cb.dataCallback(DataLayout.class,dataex);
                        }
                        Log.d(TAG, "Current ");
                    }
                });

//        colRef.orderBy("time", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for (QueryDocumentSnapshot doc: task.getResult()){
//                        Log.d(TAG, doc.getId() + " => " + doc.getData().get("time"));
//                        String[] dataex = new String[4];
//                        Timestamp timestamp = (Timestamp) doc.getData().get("time");
//                        dataex[0] = timestamp.toDate().toString() ;
//                        dataex[1] = " ";
//                        dataex[2] = " 5 ";
//                        dataex[3] = doc.getData().get("rate").toString();
//                        cb.dataCallback(DataLayout.class,dataex);
//                    }
//
//                }else {
//                    System.out.println("Auth Exception");
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        });
    }




    //functions for getting, setting, authentication




}



