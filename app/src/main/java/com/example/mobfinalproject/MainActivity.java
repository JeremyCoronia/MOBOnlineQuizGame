package com.example.mobfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    //first 2 is login; last 3 is register
    MaterialEditText editExistUsername, editExistPassword, editUsername, editPassword, editEmail;

    Button btnRegister, btnLogin;

    FirebaseDatabase userDatabase;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase component
        userDatabase = FirebaseDatabase.getInstance();
        users = userDatabase.getReference("Users");

        //login component
        editExistUsername = (MaterialEditText) findViewById(R.id.editExistUsername);
        editExistPassword = (MaterialEditText) findViewById(R.id.editExistPassword);

        //buttons for (1) register and (2) login
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(editExistUsername.getText().toString(), editExistPassword.getText().toString());
            }
        });
    }

    private void login(final String username, final String password){
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if (!username.isEmpty()){
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword().equals(password)){
                            //pasok ka na
//                            Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeActivity);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong login credentials. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(MainActivity.this, "Missing login credentials. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User does not exist in our records. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showRegisterDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Register");
        alertDialog.setMessage("Please fill up the information being requested.");

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout, null);

        //register component
        editUsername = (MaterialEditText) sign_up_layout.findViewById(R.id.editUsername);
        editPassword = (MaterialEditText) sign_up_layout.findViewById(R.id.editPassword);
        editEmail = (MaterialEditText) sign_up_layout.findViewById(R.id.editEmail);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override

            //CONSTRUCTOR: STR Username, STR Password, STR Email
            public void onClick(DialogInterface dialog, int which) {
                final User user = new User(editUsername.getText().toString(),
                                        editPassword.getText().toString(),
                                        editEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUsername()).exists()){
                            Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        } else{
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(MainActivity.this, "Account registered.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
