package org.kotakeducation.shoutbox.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.kotakeducation.shoutbox.R;

public class ProjectDisplayActivity extends AppCompatActivity {

    private TextView projectTitle,projectDesc;
    private ImageView projectImage;
    private Button deleteProject,updateProject;
    private String UserID,ProjectID;
    private FirebaseFirestore db;
    private StorageReference reference= FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_display);

        projectTitle=findViewById(R.id.ProjectTitle);
        projectDesc=findViewById(R.id.ProjectDescription);
        projectImage=findViewById(R.id.ProjectImage);
        deleteProject=findViewById(R.id.DeleteProject);
        updateProject=findViewById(R.id.UpdateProject);
        db=FirebaseFirestore.getInstance();

        Intent intent=getIntent();
        UserID=intent.getStringExtra("User ID");
        ProjectID=intent.getStringExtra("Project Id");

        display(UserID,ProjectID);

        updateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ProjectDisplayActivity.this, EditProjectActivity.class);
                intent.putExtra("User ID",UserID);
                intent.putExtra("Project Id",ProjectID);
                startActivity(intent);
                finish();
            }
        });

        deleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection(UserID).document(ProjectID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("Projects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for(DocumentSnapshot snapshot : task.getResult()){
                                            if(snapshot.getString("User ID").equals(UserID) && snapshot.getString("Project Id").equals(ProjectID)){
                                                StorageReference fileref=reference.child(snapshot.getString("Image ID"));
                                                fileref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        db.collection("Projects").document(snapshot.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(ProjectDisplayActivity.this, "Project Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(ProjectDisplayActivity.this, ProjectFeedActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(ProjectDisplayActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception exception) {
                                                        Toast.makeText(ProjectDisplayActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProjectDisplayActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProjectDisplayActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
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
                                projectTitle.setText(snapshot.getString("Project Title"));
                                projectDesc.setText(snapshot.getString("Project Desc"));
                                Glide.with(ProjectDisplayActivity.this).load(snapshot.getString("Project Image")).centerCrop().into(projectImage);
                                break;
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProjectDisplayActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}