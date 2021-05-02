package com.example.greenhouse;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText email, pass, passConfirm, user;
    Button signUp;
    TextView logIn;
    ImageView showPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String userText, emailText, passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_edit_text);
        pass = findViewById(R.id.pass_edit_text);
        passConfirm = findViewById(R.id.pass_confirm_edit_text);
        user = findViewById(R.id.username_edit_text);
        signUp = findViewById(R.id.signup_btn);
        logIn = findViewById(R.id.login_txt);
        showPassword = findViewById(R.id.show_pass_register);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = false;
                userText = user.getText().toString();
                emailText = email.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    email.setError("Not valid email");
                    b = true;
                }
                passText = pass.getText().toString();
                String pass1Text = passConfirm.getText().toString();
                if (TextUtils.isEmpty(passText)) {
                    pass.setError("Required field");
                    b = true;
                }
                if (!TextUtils.equals(passText, pass1Text)) {
                    passConfirm.setError("Not Matched");
                    b = true;
                }
                if (!b) {
                    rgister(userText, emailText);
                   /* FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(emailText, passText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(RegisterActivity.this, "User account created", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();


                        }
                    });*/

                }

            }

            private void rgister(final String user_name, final String user_email) {
                emailText = email.getText().toString();
                passText = pass.getText().toString();
                userText = user.getText().toString();

                mAuth.createUserWithEmailAndPassword(emailText, passText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser rUser = mAuth.getCurrentUser();
                            String userId = rUser.getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("userId", userId);
                            hashMap.put("user name", userText);
                            hashMap.put("user email", emailText);
                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User account created", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    } else {
                                        Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            //                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }

                });
            }

        });


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.show_pass_register) {

                    if (pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                        ((ImageView) (v)).setImageResource(R.drawable.hide_pass);

                        //Show Password
                        pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        ((ImageView) (v)).setImageResource(R.drawable.show_pass);

                        //Hide Password
                        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
