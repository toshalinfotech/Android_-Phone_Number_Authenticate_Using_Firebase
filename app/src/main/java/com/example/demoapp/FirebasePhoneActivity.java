package com.example.demoapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FirebasePhoneActivity extends AppCompatActivity implements View.OnClickListener{

    EditText phoneNumberEditText, otpNumberEditText;
    Button sendOTPButton, resendOTPButton, verifyOTPButton;

    //Add it below the lines where you declared the fields
    private FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    //Add this on top where other variables are declared
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_phone);

        phoneNumberEditText = (EditText)findViewById(R.id.phoneNumberEditText);
        otpNumberEditText = (EditText)findViewById(R.id.otpNumberEditText);

        sendOTPButton = (Button)findViewById(R.id.sendOTPButton);
        resendOTPButton = (Button)findViewById(R.id.resendOTPButton);
        verifyOTPButton = (Button)findViewById(R.id.verifyOTPButton);

        sendOTPButton.setOnClickListener(this);
        resendOTPButton.setOnClickListener(this);
        verifyOTPButton.setOnClickListener(this);

        //Add it in the onCreate method, after calling method initFields()
        mAuth = FirebaseAuth.getInstance();
        initFireBaseCallbacks();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendOTPButton:
                String mobile = phoneNumberEditText.getText().toString().trim();
                if(mobile.isEmpty() || mobile.length() < 10){
                    phoneNumberEditText.setError("Enter a valid mobile Number");
                    phoneNumberEditText.requestFocus();
                }else {
                    phoneNumberEditText.setError(null);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                           "+91" + mobile,        // Phone number to verify
                            1,                 // Timeout duration
                            TimeUnit.MINUTES,   // Unit of timeout
                            this,               // Activity (for callback binding)
                            mCallbacks);
                }
                break;
            case R.id.resendOTPButton:
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumberEditText.getText().toString(),        // Phone number to verify
                        1  ,               // Timeout duration
                        TimeUnit.MINUTES,   // Unit of timeout
                        this,               // Activity (for callback binding)
                        mCallbacks,         // OnVerificationStateChangedCallbacks
                        mResendToken);
                break;
            case R.id.verifyOTPButton:
                String otpCode = otpNumberEditText.getText().toString().trim();
                if (otpCode.isEmpty() || otpCode.length() < 6) {
                    otpNumberEditText.setError("Enter valid code");
                    otpNumberEditText.requestFocus();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otpCode);
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = task.getResult().getUser();
                                        Toast.makeText(FirebasePhoneActivity.this, "Verification Success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            Toast.makeText(FirebasePhoneActivity.this, "Verification Failed, Invalid credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
                break;
        }
    }

    ///Add this method below auth initialization in the onCreate method.
    void initFireBaseCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                //Toast.makeText(FirebasePhoneActivity.this, "Verification Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(FirebasePhoneActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(FirebasePhoneActivity.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId; //Add this line to save //verification Id
                mResendToken = token; //Add this line to save the resend token
                phoneNumberEditText.setVisibility(View.GONE);
                sendOTPButton.setVisibility(View.GONE);
                otpNumberEditText.setVisibility(View.VISIBLE);
                verifyOTPButton.setVisibility(View.VISIBLE);
            }
        };
    }
}
