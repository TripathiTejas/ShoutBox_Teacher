package org.kotakeducation.shoutbox.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.kotakeducation.shoutbox.R;

public class LoginScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,passWord;
    private MaterialButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

                mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginScreenActivity.this, ProjectFeedActivity.class);
            startActivity(intent);
            finish();
        }

        email=findViewById(R.id.email);
        passWord=findViewById(R.id.password);
        signInButton= findViewById(R.id.signInButton);

        signInButton.setOnClickListener(v -> {
            String email = LoginScreenActivity.this.email.getText().toString();
            String password = passWord.getText().toString();

            Toast.makeText(LoginScreenActivity.this, "Signing in..", Toast.LENGTH_SHORT).show();
            if(email.isEmpty()){
                LoginScreenActivity.this.email.setError("E-mail is required!");
                LoginScreenActivity.this.email.requestFocus();
                return;
            }
            if(password.isEmpty()){
                passWord.setError("Password is required!");
                passWord.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                LoginScreenActivity.this.email.setError("Please provide a valid email!");
                LoginScreenActivity.this.email.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginScreenActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginScreenActivity.this, UserInfo.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginScreenActivity.this, "Invalid Credentials or User not registered", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}