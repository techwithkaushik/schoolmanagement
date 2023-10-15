package com.ask2784.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;

import com.ask2784.schoolmanagement.databinding.ActivityLoginBinding;
import com.ask2784.schoolmanagement.models.User;
import com.ask2784.schoolmanagement.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    
    private final TextWatcher watcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            
        }
        
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            String emailTxt = ""+ binding.loginEmail.getText();
            String passTxt = ""+ binding.loginPass.getText();
            
            if (binding.loginEmail.isFocused()) {
                if (emailTxt.isEmpty()) {
                    binding.loginEmail.setError("Enter email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
                    binding.loginEmail.setError("!invalid email");
                } else {
                    binding.loginEmail.setError(null);
                }
            }
            
            if (binding.loginPass.isFocused()) {
                if (passTxt.isEmpty()) {
                    binding.loginPass.setError("Enter password");
                } else if (passTxt.length() < 6) {
                    binding.loginPass.setError("password should be min. 6 characters");
                } else {
                    binding.loginPass.setError(null);
                }
            }

            binding.login.setEnabled(
                !emailTxt.isEmpty()
                && Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()
                && passTxt.length() >= 6);
        }
        
        @Override
        public void afterTextChanged(Editable arg0) {
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Utils.setupUI(binding.getRoot(),this);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            checkUser(mAuth.getCurrentUser().getUid());
        }
        else{
            showLoading(false);
        }
        checkEmailPass();
        loginUser();
    }

    private void checkUser(String uId) {
        if(!uId.isEmpty()){
            FirebaseFirestore.getInstance().collection("users").document(uId).get().addOnSuccessListener(documentSnapshot->{
                    if(documentSnapshot.exists()){
                        User user = documentSnapshot.toObject(User.class);
                        if(uId.equals(user.getUId())){
                            if(user.getUserType().equals("admin")) {
                                Intent i = new Intent(LoginActivity.this,AdminActivity.class);
                                i.putExtra("uId",uId);
                                i.putExtra("userType",user.getUserType());
                                startUserActivity(i);
                            }else if(user.getUserType().equals("teacher")){
                                startUserActivity(new Intent(LoginActivity.this,TeacherActivity.class));
                            }else if(user.getUserType().equals("student")){
                                startUserActivity(new Intent(LoginActivity.this,MainActivity.class));
                            } else {
                                showLoading(false);
                                Snackbar.make(binding.getRoot(),"There is Problem with this Account.\nPlease contect to admin",Snackbar.LENGTH_SHORT).show();
                                mAuth.signOut();
                            }
                        }else{
                            showLoading(false);
                            Snackbar.make(binding.getRoot(),"You are not a valid user.",Snackbar.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                    }else{
                        showLoading(false);
                        Snackbar.make(binding.getRoot(),"This Account is not exist.\nPlease contect to admin",Snackbar.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }
            }).addOnFailureListener(e->{
                    showLoading(false);
                    Snackbar.make(binding.getRoot(),e.getLocalizedMessage(),Snackbar.LENGTH_SHORT).show();
                    mAuth.signOut();
            });
        }else{
            showLoading(false);
        }
    }
    
    private void checkEmailPass() {
        binding.loginEmail.addTextChangedListener(watcher);
        binding.loginPass.addTextChangedListener(watcher);
    }
    
    private void loginUser() {
        binding.login.setOnClickListener(v->{
                showLoading(true);
                String email = ""+ binding.loginEmail.getText().toString();
                String pass = ""+ binding.loginPass.getText().toString();
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser firebaseUser = task.getResult().getUser();
                                checkUser(firebaseUser.getUid());
                            }else{
                                showLoading(false);
                                Snackbar.make(binding.getRoot(),"Login: " + task.getException().getLocalizedMessage(),Snackbar.LENGTH_SHORT).show();
                            }
                        }
                }).addOnFailureListener(this,new OnFailureListener(){
                        @Override
                        public void onFailure(Exception e) {
                            showLoading(false);
                            Snackbar.make(binding.getRoot(),"Failed: " + e.getLocalizedMessage().toString(),Snackbar.LENGTH_SHORT).show();
                        }
                });
        });
    }
    
    private void startUserActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
    
    private void showLoading(boolean isLoading) {
        binding.showLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.loginView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }
}
