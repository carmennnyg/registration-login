package com.example.eboxsalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity {
    private Button mSignUpButton, mSignInButton;
    private TextView mSignInTextView, mBannerTextView;
    private EditText mNameField, mPhoneField, mEmailField, mPassword1Field;
    private View mView;
    FirebaseAuth fAuth;
    FirebaseFirestore db;

    public static  final  String COLLECTION_NAME_KEY = "USERS";
    public static  final  String NAME_KEY = "name";
    public static  final  String PASSWORD_KEY = "password";
    public static  final  String NUMBER_KEY = "phoneNo:";
    public static  final  String EMAIL_KEY = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
        mSignUpButton = findViewById(R.id.sign_up_button);
        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInTextView = findViewById(R.id.sign_in_text_view);
        mBannerTextView= findViewById(R.id.banner_text_view);
        mNameField = findViewById(R.id.name_edit_text);
        mPhoneField= findViewById(R.id.phone_edit_text);
        mEmailField = findViewById(R.id.emailaddress_edit_text);
        mPassword1Field= findViewById(R.id.password1_edit_text);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        db = FirebaseFirestore.getInstance();

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mPhoneField.getText().toString().trim();
                String email = mEmailField.getText().toString().trim();
                String pwd = mPassword1Field.getText().toString().trim();

                if(phone.length() > 11 || phone.length() < 9){
                    mPhoneField.setError("Invalid phone number.");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmailField.setError("Please enter an email address");
                    return;
                }

                if(TextUtils.isEmpty(pwd)){
                    mPassword1Field.setError("Please enter your password");
                    return;
                }

                if(pwd.length() < 11){
                    mPassword1Field.setError("Password must be >= 11 characters");
                    return;
                }

                else if (!mNameField.getText().toString().equals("")
                        && !mPhoneField.getText().toString().equals("")
                        && !mEmailField.getText().toString().equals("")
                        && !mPassword1Field.getText().toString().equals("")) {
                    CollectionReference cities = db.collection(COLLECTION_NAME_KEY);
                    final Users users = new Users();

                    users.setName(mEmailField.getText().toString());
                    users.setPassword(mPassword1Field.getText().toString());
                    db.collection(COLLECTION_NAME_KEY).document(mEmailField.getText().toString()).set(users);

                    DocumentReference docRef = db.collection(COLLECTION_NAME_KEY).document();

                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Toast.makeText(RegisterActivity.this,
                                        "All ready registered",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                users.setName(mEmailField.getText().toString());
                                users.setPassword(mPassword1Field.getText().toString());
                                db.collection(COLLECTION_NAME_KEY).document(mEmailField.getText().toString()).set(users);

                                Toast.makeText(RegisterActivity.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Make All field Filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}