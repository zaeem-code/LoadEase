package com.example.loadease.SignIn_signUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loadease.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private EditText phoneNumber;
    LinearLayout send_otp;

    ProgressBar progressBar;
    CountryCodePicker ccp;

    String username;

    String c1,c2,c3,c4,c5,c6,phone;
    String check="";
    LinearLayout linearLayour;
    Bundle bundle;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.su_prg_bar);

        phoneNumber = findViewById(R.id.ed_ph_number);
        send_otp = findViewById(R.id.send_otp);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!phoneNumber.getText().toString().trim().isEmpty() & phoneNumber.getText().toString().length() == 10) {

                    String phnNo = "+" + ccp.getSelectedCountryCode() + phoneNumber.getText().toString();
                    Log.d(TAG, "OnClick: Phone Number......." + phnNo);


                    verifyStarting();


                } else {
                    phoneNumber.setError("Phone Number is not Valid");
                }
                progressBar.setVisibility(View.VISIBLE);
                send_otp.setVisibility(View.INVISIBLE);
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    public void verifyStarting() {
            if (check.equals("s")) {

                progressDialog.show();
                try {

                    phone = bundle.getString("Phone");
                    username = bundle.getString("Name");
                    Toast.makeText(getApplicationContext(), check + phoneNumber + username, Toast.LENGTH_LONG).show();

                    if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(username)) {
                        Toast.makeText(getApplicationContext(), phoneNumber + username, Toast.LENGTH_LONG).show();

                        verificationStep1(phone.trim());
                    } else {
                        Snackbar.make(linearLayour, "A field is missing\n contact developers", Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                }
            } else {
                if (check.equals("f")) {

                    progressDialog.show();
                    try {
                        username = bundle.getString("Name");
                        phone = bundle.getString("Phn");

                        if (!TextUtils.isEmpty(phone)) {

                            Toast.makeText(getApplicationContext(), phone, Toast.LENGTH_LONG).show();
                            verificationStep1(phone.trim());
                        } else {

                            Toast.makeText(getApplicationContext(), phone, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                    }
                }
            }


        }


    public void verificationStep1(String p){


// sends code requexst sendxnxxxxx
        Toast.makeText(getApplicationContext(),p,Toast.LENGTH_LONG).show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(p,90,TimeUnit.SECONDS,this,mCallbacks);
    }
    String code,mVerificationId;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            progressDialog.show();
            code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(),
                    "Unsuccessful\n maybe number already exist contact admins\n Error code: x\n"+e.getMessage(), Toast.LENGTH_LONG).show();


        }

        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificationId, forceResendingToken);
            Toast.makeText(getApplicationContext(),
                    "codesent", Toast.LENGTH_LONG).show();

            progressDialog.dismiss();
            mVerificationId = verificationId;

        }
    };

    //
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "signInWithCredential:success", Toast.LENGTH_SHORT).show();

                            //   FirebaseUser user = task.getResult().getUser(); we are not reginstring auth based
                            if (check.equals("s")){

                                Toast.makeText(getApplicationContext(),"s2",Toast.LENGTH_LONG).show();
                                firebaseExecutionsigup();
                            }else {
                                if (check.equals("f")){

                                    Toast.makeText(getApplicationContext(),"f2",Toast.LENGTH_LONG).show();

                                    firebaseExecutionforet();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }




    public void firebaseExecutionsigup(){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UsersDB");
        myRef.child("Name").setValue(username);
        myRef.child("PhoneNumber").setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    bundle.clear();
                    finish();
                    Toast.makeText(getApplicationContext(), "The verification is successful\nLog in with registerd number", Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                    //  startActivity(new Intent(this,));

                    check="";
                    finish();

                }else {
                    Snackbar.make(linearLayour,"un-expected Error accured\n failed to signup propery click try again",Snackbar.LENGTH_LONG).show();


                    progressDialog.dismiss();
                    check="";
                    bundle.clear();
                    finish();
                }
            }
        });
    }



    public void firebaseExecutionforet(){

        Bundle bundle2=new  Bundle();
        bundle2.putString("Name",username);
        bundle2.putString("Phn",phone);
        bundle.clear();

        finish();
    }
}