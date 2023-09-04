package com.example.cat3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity
{
    String userID;
    private FirebaseAuth mAuth;
    private EditText name, password, registernumber, department, specialization;
    private RadioGroup radioGroupSex;
    private RadioButton radioButtonMale, radioButtonFemale;
    private NumberPicker age;
    private Button registerbtn;
    private TextView loginlink;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.email);
        password = findViewById(R.id.password);
        radioGroupSex = findViewById(R.id.radioGroupSex);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        registernumber = findViewById(R.id.registernumber);
        department = findViewById(R.id.fullname);
        specialization = findViewById(R.id.position);
        registerbtn = findViewById(R.id.registerbtn);
        loginlink = findViewById(R.id.loginlink);


        registerbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Get values from input fields
                String nm = name.getText().toString();
                String pswd = password.getText().toString();
                String sex = radioButtonMale.isChecked() ? "Male" : "Female";
                String regno = registernumber.getText().toString();
                String dept = department.getText().toString();
                String spl = specialization.getText().toString();


                if(nm.isEmpty() && pswd.isEmpty())
                {
                    Toast.makeText(register.this, "Enter your details", Toast.LENGTH_SHORT).show();
                }

                String message = "Name: " + nm +
                        "\nPassword: " + pswd +
                        "\nSex: " + sex +
                        "\nRegister Number: " + regno +
                        "\nDepartment: " + dept +
                        "\nSpecialization: " +spl;
                Toast.makeText(register.this, message, Toast.LENGTH_LONG).show();

                //login and register
                mAuth.createUserWithEmailAndPassword(nm, pswd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(register.this, "Successfully Registered.", Toast.LENGTH_SHORT).show();
                                    userID = mAuth.getCurrentUser().getUid();

                                    //firestore
                                    // Create a new user with a first and last name
                                    DocumentReference documentReference = db.collection("users").document(userID);

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", nm);
                                    user.put("registernumber", regno);
                                    user.put("department", dept);
                                    user.put("specialization", spl);

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            String TAG1 = "Success";
                                            Log.d(TAG1, "Onsuccess: User Profile is created for " + userID);
                                        }
                                    });

                                    Intent intent = new Intent(register.this, login.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        //Make the register text underlined and clickable
        SpannableString spannableString = new SpannableString("Already a member? Login");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        spannableString.setSpan(new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {
                redirectToLoginPage();
            }
        }, 0, spannableString.length(), 0);
        loginlink.setText(spannableString);
        loginlink.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());

    }


    public void redirectToLoginPage()
    {
        Intent intent=new Intent(this, login.class);
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