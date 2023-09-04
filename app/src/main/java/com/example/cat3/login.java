package com.example.cat3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText username,password;

    Button loginbtn;
    TextView registerlink;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialization
        mAuth = FirebaseAuth.getInstance();
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginbtn=findViewById(R.id.loginbtn);
        registerlink=findViewById(R.id.registerlink);

        loginbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String enteredusername=username.getText().toString();
                String enteredpassword=password.getText().toString();

                if (enteredusername.isEmpty() & enteredpassword.isEmpty()) {
                    Toast.makeText(login.this, "Enter your credentials", Toast.LENGTH_SHORT).show();
                }

                mAuth.signInWithEmailAndPassword(enteredusername, enteredpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(login.this, MainActivity.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
                /*//check for valid credentials
                if (enteredusername.equals("Jasleen") && enteredpassword.equals("yogaforlife"))
                {
                    redirectToHomePage();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }*/
        });

        //Make the register text underlined and clickable
        SpannableString spannableString = new SpannableString("Not a member? Register");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        spannableString.setSpan(new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {
                redirectToRegisterPage();
            }
        }, 0, spannableString.length(), 0);
        registerlink.setText(spannableString);
        registerlink.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());


    }

    public void redirectToRegisterPage()
    {
        Intent intent=new Intent(login.this, register.class);
        startActivity(intent);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
}