package org.kotakeducation.shoutbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

public class EditProject extends AppCompatActivity {

    private EditText ProjectTitle,ProjectDesc;
    private ImageView projectImage;
    private Button UpdateProject;
    private String UserID,ProjectID;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        ProjectTitle=findViewById(R.id.ProjectTitle);
        ProjectDesc=findViewById(R.id.ProjectDescription);
        projectImage=findViewById(R.id.ProjectImage);
        UpdateProject=findViewById(R.id.UpdateProject);
        db=FirebaseFirestore.getInstance();

        Intent intent=getIntent();
        UserID=intent.getStringExtra("User ID");
        ProjectID=intent.getStringExtra("Project Id");

        display(UserID,ProjectID);

        UpdateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProjectTitle.getText().toString().trim().length() > 0 && ProjectDesc.getText().toString().trim().length() > 0)
                updateProject(UserID,ProjectID);
                else
                    Toast.makeText(EditProject.this, "Empty Fields Not Allowed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateProject(String UserID,String ProjectID){
        DocumentReference documentReference = db.collection(UserID).document(ProjectID);
        documentReference
                .update("Project Title", ProjectTitle.getText().toString(),"Project Desc",ProjectDesc.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("Projects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(DocumentSnapshot snapshot : task.getResult()){
                                    if(snapshot.getString("User ID").equals(UserID) && snapshot.getString("Project Id").equals(ProjectID)){
                                        updateCommonList(snapshot.getId());
                                        break;
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProject.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProject.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateCommonList(String DocumentID){
        DocumentReference documentReference = db.collection("Projects").document(DocumentID);

        documentReference
                .update("Project Title", ProjectTitle.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(EditProject.this,ProjectFeed.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProject.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void display(String UserID,String ProjectID){

        db.collection(UserID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot snapshot : task.getResult()){
                            if(ProjectID.equals(snapshot.getId())) {
                                ProjectTitle.setText(snapshot.getString("Project Title"));
                                ProjectDesc.setText(snapshot.getString("Project Desc"));
                                Glide.with(EditProject.this).load(snapshot.getString("Project Image")).centerCrop().into(projectImage);
                                break;
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProject.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}