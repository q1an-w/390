package com.example.app_390.database;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.app_390.database.models.FormattedData;
import com.example.app_390.database.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class FirebaseController {
    private static boolean access;
    private Map<String,Object> data;
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
    public void loadAuthData(String username, String pwd, MyCallback callback) {
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
                    }catch(Exception e){
                        callback.onCallback(false);
                        return;
                    }

                    if(input.equals(pwd)){
                        auth = true;
                    }else;
                    callback.onCallback(auth);
                } else {
                    callback.onCallback(false);
                    System.out.println("Auth Exception");
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public boolean getAccess(){
        return access;
    }


    //functions for getting, setting, authentication




}



