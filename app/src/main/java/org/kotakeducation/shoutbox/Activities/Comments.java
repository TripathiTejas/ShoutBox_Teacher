package org.kotakeducation.shoutbox.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.kotakeducation.shoutbox.Adapters.AdapterComments;
import org.kotakeducation.shoutbox.Adapters.AdapterProjectFeed;
import org.kotakeducation.shoutbox.Models.ModelForComments;
import org.kotakeducation.shoutbox.Models.ModelForProjectFeed;
import org.kotakeducation.shoutbox.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comments extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String UserID,ProjectID;
    private AdapterComments adapter;
    private List<ModelForComments> list;
    private FirebaseFirestore db;
    private FloatingActionButton AddComment;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        recyclerView=findViewById(R.id.displayComment);
        db=FirebaseFirestore.getInstance();
        AddComment=findViewById(R.id.AddComment);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter= new AdapterComments(this,list);
        recyclerView.setAdapter(adapter);

        Intent intent=getIntent();
        UserID=intent.getStringExtra("User ID");
        ProjectID=intent.getStringExtra("Project Id");

        showComments(UserID,ProjectID);

        AddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddComment(UserID,ProjectID);
            }
        });

    }

    public void showComments(String UserID,String ProjectID){
        db.collection(UserID).document(ProjectID).collection("Comments").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for(DocumentSnapshot snapshot : task.getResult()){
                            ModelForComments model =new ModelForComments(snapshot.getString("User Name"),snapshot.getString("Comment"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Comments.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AddComment(String UserID, String ProjectID){
        final AlertDialog.Builder alert = new AlertDialog.Builder(Comments.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        final EditText txt_inputText = (EditText)mView.findViewById(R.id.txt_input);
        MaterialButton btn_cancel = mView.findViewById(R.id.Cancel);
        MaterialButton add_comment = mView.findViewById(R.id.saveComment);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String userID= currentUser.getUid();

                db.collection("Teacher Info").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(DocumentSnapshot snapshot : task.getResult()){
                                    if(userID.equals(snapshot.getId())) {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("User Name",snapshot.getString("FULL NAME"));
                                        map.put("Comment",txt_inputText.getText().toString());

                                        db.collection(UserID).document(ProjectID).collection("Comments")
                                                .add(map)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(Comments.this, "Comment Added Successfully", Toast.LENGTH_SHORT).show();
                                                        alertDialog.dismiss();
                                                        showComments(UserID,ProjectID);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Comments.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        break;
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Comments.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

}