package com.example.app_390.DATABASE;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseController {
    private FirebaseFirestore db;
    FirebaseController(){
        db =FirebaseFirestore.getInstance();

    }

}
