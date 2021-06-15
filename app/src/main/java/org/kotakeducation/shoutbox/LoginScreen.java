package org.kotakeducation.shoutbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,passWord;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

                mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginScreen.this,ProjectFeed.class);
            startActivity(intent);
            finish();
        }

        email=findViewById(R.id.email);
        passWord=findViewById(R.id.password);
        signInButton= findViewById(R.id.signInButton);

        signInButton.setOnClickListener(v -> {
            String email = LoginScreen.this.email.getText().toString();
            String password = passWord.getText().toString();
            if(email.isEmpty()){
                LoginScreen.this.email.setError("E-mail is required!");
                LoginScreen.this.email.requestFocus();
                return;
            }
            if(password.isEmpty()){
                passWord.setError("Password is required!");
                passWord.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                LoginScreen.this.email.setError("Please provide a valid email!");
                LoginScreen.this.email.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginScreen.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginScreen.this,ProjectFeed.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginScreen.this, "Invalid Credentials or User not registered", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}