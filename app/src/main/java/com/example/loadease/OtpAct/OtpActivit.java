package com.example.loadease.OtpAct;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.concurrent.TimeUnit;

public class OtpActivit extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2,e3,e4,e5,e6;
    String c1,c2,c3,c4,c5,c6,phone,pass,username,email;
    LinearLayout verifybtn;
    String check="";
    LinearLayout linearLayour;
    Bundle bundle;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        e1=findViewById(R.id.edone);
        e2=findViewById(R.id.edtwo);
        e3=findViewById(R.id.edthree);
        e4=findViewById(R.id.edfour);
        e5=findViewById(R.id.edfive);
        e6=findViewById(R.id.edsix);
        verifybtn=findViewById(R.id.verify);

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OtpActivit.this, "jgjbm", Toast.LENGTH_SHORT).show();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("verifying...");


        /////////////////
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c1=s.toString().trim();
                if (TextUtils.isEmpty(c1)){

                    e1.requestFocus();
                }else {

                    e2.requestFocus();
                }
            }
        });
        //e2/////////////////////////
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c2=s.toString().trim();
                if (TextUtils.isEmpty(c2)){

                    e1.requestFocus();
                }else {

                    e3.requestFocus();
                }
            }
        });
        //e3/////////////////////////
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c3=s.toString().trim();
                if (TextUtils.isEmpty(c3)){

                    e2.requestFocus();
                }else {

                    e4.requestFocus();
                }
            }
        });
        //e4/////////////////////////
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c4=s.toString().trim();
                if (TextUtils.isEmpty(c4)){

                    e3.requestFocus();
                }else {

                    e5.requestFocus();
                }
            }
        });
        //e5/////////////////////////
        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c5=s.toString().trim();
                if (TextUtils.isEmpty(c5)){

                    e4.requestFocus();
                }else {

                    e6.requestFocus();
                }
            }
        });
        //e6/////////////////////////
        e6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c6=s.toString().trim();
                if (TextUtils.isEmpty(c6)){

                    e5.requestFocus();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        bundle = getIntent().getExtras();
        check=bundle.getString("chk");

        Toast.makeText(getApplicationContext(),check,Toast.LENGTH_LONG).show();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify:
                progressDialog.show();
                gettingcode();
                break;

        }
    }


    public void gettingcode(){
        String smsCode;
        c1=e1.getText().toString().trim();
        c2=e2.getText().toString().trim();
        c3=e3.getText().toString().trim();
        c4=e4.getText().toString().trim();
        c5=e5.getText().toString().trim();
        c6=e6.getText().toString().trim();
        if (!TextUtils.isEmpty(c1)&&!TextUtils.isEmpty(c2)&&!TextUtils.isEmpty(c3)&&!TextUtils.isEmpty(c4) &&
                !TextUtils.isEmpty(c5)&&!TextUtils.isEmpty(c6)){
            smsCode=c1+c2+c3+c4+c5+c6;
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, smsCode.trim());
            signInWithPhoneAuthCredential(credential);
        }else {
            e1.setError("");
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
        myRef.child(email).child("Name").setValue(username);
        myRef.child(email).child("PhoneNumber").setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
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
