package com.example.cat3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity
{

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    private EditText name, registernumber, department, specialization;
    Button updateBtn,deleteBtn;

    ImageView backButton;

    String userID;

    String oldName;
    String newName, newRegno, newDept, newSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        name = findViewById(R.id.email);
        registernumber = findViewById(R.id.registernumber);
        department = findViewById(R.id.fullname);
        specialization = findViewById(R.id.position);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        backButton = findViewById(R.id.backButton);


        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        //retrieve
        if (currentUser != null) {

            userID = currentUser.getUid();

            DocumentReference documentReference = db.collection("users").document(userID);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot != null) {
                        name.setText(documentSnapshot.getString("name"));
                        registernumber.setText(documentSnapshot.getString("registernumber"));
                        department.setText(documentSnapshot.getString("department"));
                        specialization.setText(documentSnapshot.getString("specialization"));
                        oldName = name.getText().toString();
                        Toast.makeText(profile.this, "oldname = "+oldName,Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


        //update
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newName = name.getText().toString();
                newRegno = registernumber.getText().toString();
                newDept = department.getText().toString();
                newSpec = specialization.getText().toString();

                db.collection("users").whereEqualTo("name", oldName).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                    Toast.makeText(profile.this, "hi i am inside successful",Toast.LENGTH_SHORT).show();
                                    userID = firebaseAuth.getCurrentUser().getUid();

                                    DocumentReference dr = db.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", newName);
                                    user.put("registernumber", newRegno);
                                    user.put("department", newDept);
                                    user.put("specialization", newSpec);


                                    dr.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            String TAG1 = "Status";
                                            Log.d(TAG1, "Updated " + userID);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle the failure
                                            String TAG1 = "Status";
                                            Log.d(TAG1, "failed updating " + userID);
                                        }
                                    });
                                } else {
                                    Toast.makeText(profile.this, "hi i am unsuccesful",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        //delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAcc();
            }
        });


        //back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //delete
    private void deleteAcc() {
        db.collection("users").whereEqualTo("name", oldName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentId = documentSnapshot.getId();

                            db.collection("users").document(documentId).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(profile.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(profile.this, "Some Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(profile.this, "Delete failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}