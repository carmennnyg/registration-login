package com.example.eboxsalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPwdActivity extends AppCompatActivity {
    private Button mSendButton;
    private EditText mEmail2;
    private TextView mForgotPwdTextView, mBannerTextView;
    private ProgressBar mProgressBar;
    private ImageView mBackImageView, mLockImageView;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        mSendButton = findViewById(R.id.send_button);
        mEmail2 = findViewById(R.id.email2_edit_text);
        mForgotPwdTextView = findViewById(R.id.forgotpwd_text_view);
        mBannerTextView = findViewById(R.id.banner_text_view2);
        mProgressBar = findViewById(R.id.progressBar);
        mBackImageView = findViewById(R.id.back_image_view);
        mLockImageView = findViewById(R.id.lock_image_view);


        fAuth = FirebaseAuth.getInstance();

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ForgotPwdActivity.this, LoginActivity.class);
                startActivity(back);
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                fAuth.sendPasswordResetEmail(mEmail2.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPwdActivity.this,
                                    "Password will send to your email", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(ForgotPwdActivity.this,
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}