package com.example.app_390.DATABASE;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.app_390.DATABASE.models.FormattedData;
import com.example.app_390.DATABASE.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.util.CustomClassMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseController {
    private FirebaseFirestore db;
    public FirebaseController(){
        db =FirebaseFirestore.getInstance();
    }
    public void testAddUser(User user){
        //test
        Map<String,FormattedData> data = new HashMap<>();
        for(Integer i = 0; i <10;i++){
            data.put(i.toString(),new FormattedData(true));
        }


        //test end
        db.collection("390users").document("profile-"+user.getUsername()).set(user);
        db.collection("390users").document("data-"+user.getUsername()).set(data);
    }
    //functions for getting, setting, authentication


}
