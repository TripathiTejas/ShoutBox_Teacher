package org.kotakeducation.shoutbox.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.kotakeducation.shoutbox.R;

import java.util.HashMap;

public class UserInfo extends AppCompatActivity {

    private EditText FullName,Age,SchoolName,Address;
    private Button NextButton;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        FullName=findViewById(R.id.FullName);
        Age=findViewById(R.id.Age);
        SchoolName=findViewById(R.id.SchoolName);
        Address=findViewById(R.id.Address);
        NextButton=findViewById(R.id.NextButton);
        db=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID= currentUser.getUid();

        getInfoFromDb();



        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName,age,schoolName,address;
                fullName=FullName.getText().toString();
                age=Age.getText().toString();
                schoolName=SchoolName.getText().toString();
                address=Address.getText().toString();

                if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(age) || TextUtils.isEmpty(schoolName) || TextUtils.isEmpty(address)){
                    Toast.makeText(UserInfo.this, "Cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String,String> userMap = new HashMap<>();
                    userMap.put("FULL NAME",fullName);
                    userMap.put("AGE",age);
                    userMap.put("SCHOOL NAME",schoolName);
                    userMap.put("ADDRESS",address);

                    db.collection("Teacher Info").document(userID).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UserInfo.this, "Saving details", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserInfo.this,ProjectFeedActivity.class);
                            startActivity(intent);
                        }
                    }) .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserInfo.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void getInfoFromDb() {
        db.collection("Teacher Info").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FullName.setText(documentSnapshot.get("FULL NAME").toString());
                    Age.setText(documentSnapshot.get("AGE").toString());
                    SchoolName.setText(documentSnapshot.get("SCHOOL NAME").toString());
                    Address.setText(documentSnapshot.get("ADDRESS").toString());
                }
            }
        });
    }
}