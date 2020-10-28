package com.example.eboxsalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private Button mSignInButton, mRegisterButton;
    private TextView mWelcomeTextView, mForgotPasswordTextView, mNoAccountTextView;
    private EditText mUsernameEmailField, mPasswordField;
    List<Users> Users_LIST;
    FirebaseFirestore db;

    public static final String COLLECTION_NAME_KEY = "USERS";
    public static final String NAME_KEY = "name";
    public static final String PASSWORD_KEY = "password";
    public static final String NUMBER_KEY = "phoneNo:";
    public static final String EMAIL_KEY = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        mSignInButton = findViewById(R.id.log_in_button);
        mRegisterButton = findViewById(R.id.register_button);
        mWelcomeTextView = findViewById(R.id.welcome_text_view);
        mForgotPasswordTextView = findViewById(R.id.forgot_password_text_view);
        mNoAccountTextView = findViewById(R.id.register_text_view);
        mUsernameEmailField = findViewById(R.id.email_edit_text);
        mPasswordField = findViewById(R.id.password_edit_text);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mUsernameEmailField.getText().toString().equals("")
                        && !mPasswordField.getText().toString().equals("")) {
                    DocumentReference docRef = db.collection(COLLECTION_NAME_KEY).document(mUsernameEmailField.getText().toString());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Users user = documentSnapshot.toObject(Users.class);

                                if (user.getPassword().equals(mPasswordField.getText().toString())) {
                                    Toast.makeText(getApplicationContext(), "Welcome to Ebox Salon:)", Toast.LENGTH_SHORT).show();
                                    Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(main);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Password Mismatching.", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Please check your email! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this, "Email or Password Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent forgot = new Intent(LoginActivity.this, ForgotPwdActivity.class);
                startActivity(forgot);
            }
        });
    }
}
