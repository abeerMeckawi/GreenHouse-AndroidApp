package com.example.greenhouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button logIn;
    TextView signUp, forgetPassword;
    ImageView showPassword;
    FirebaseAuth mAuth;
    private String text_email;
    private String text_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.pass_edit_text);
        logIn = findViewById(R.id.login_btn);
        signUp = findViewById(R.id.signup_txt);
        showPassword = findViewById(R.id.show_password_icon);
        forgetPassword = findViewById(R.id.forget_pass_txt);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String pass = password.getText().toString();

            /*   if ((TextUtils.isEmpty(emailText) || TextUtils.isEmpty(pass))) {
                    Toast.makeText(LoginActivity.this, "All Fields Required", Toast.LENGTH_LONG).show();

                } else {
                    login(text_email,text_password);

                }*/


                mAuth.signInWithEmailAndPassword(emailText, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "User Login successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText restMail = new EditText(v.getContext());
                AlertDialog.Builder passwordRestDialog = new AlertDialog.Builder(v.getContext());
                passwordRestDialog.setTitle("Reset Password");
                passwordRestDialog.setMessage("Enter Your Mail To Received Reset Link.");
                passwordRestDialog.setView(restMail);

                passwordRestDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = restMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Send" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordRestDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordRestDialog.create().show();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.show_password_icon) {

                    if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                        ((ImageView) (v)).setImageResource(R.drawable.hide_pass);

                        //Show Password
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        ((ImageView) (v)).setImageResource(R.drawable.show_pass);

                        //Hide Password
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

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

   /* private void login(  String text_email,String text_password) {
        mAuth.signInWithEmailAndPassword(text_email,text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this,"User Login successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/
}
