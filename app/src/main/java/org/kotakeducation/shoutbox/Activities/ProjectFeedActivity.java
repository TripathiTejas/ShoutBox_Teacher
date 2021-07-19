package org.kotakeducation.shoutbox.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.kotakeducation.shoutbox.Adapters.AdapterProjectFeed;
import org.kotakeducation.shoutbox.Models.ModelForProjectFeed;
import org.kotakeducation.shoutbox.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectFeedActivity extends AppCompatActivity {

    ExtendedFloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    //private FirebaseAuth mAuth;
    private AdapterProjectFeed adapter;
    //private ImageView log_out;
    private List<ModelForProjectFeed> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_feed);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter= new AdapterProjectFeed(this,list);
        recyclerView.setAdapter(adapter);
        showData();

        floatingActionButton=findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectFeedActivity.this, AddProjectActivity.class);
                intent.putExtra("activity", "feed");
                startActivity(intent);
            }
        });

    }

    public void showData(){
        db.collection("Projects").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for(DocumentSnapshot snapshot : task.getResult()){
                            ModelForProjectFeed model =new ModelForProjectFeed(snapshot.getString("Project Title"),snapshot.getString("User Name"),snapshot.getString("Date of Upload"),snapshot.getString("User ID"),snapshot.getString("Project Id"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProjectFeedActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editProfile(View view) {
        startActivity(new Intent(ProjectFeedActivity.this, UserInfo.class));
    }

    public void logout(View view) {

        new MaterialAlertDialogBuilder(ProjectFeedActivity.this)
                .setTitle("Logout?")
                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(ProjectFeedActivity.this, LoginScreenActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}