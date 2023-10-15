package com.ask2784.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.ask2784.schoolmanagement.databinding.ActivityAdminBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Method;

public class AdminActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    
    private ActivityAdminBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.adminL.toolbar);
        mAuth = FirebaseAuth.getInstance();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.admin_signout) {
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
            try{
                Method m = menu.getClass().getDeclaredMethod(
                    "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            }
            catch(NoSuchMethodException e){
                Log.d("AdminActivity",e.toString());
                e.printStackTrace();
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }
    
    

    @Override
    public void onAuthStateChanged(FirebaseAuth auth) {
        if(auth.getCurrentUser() == null) {
            startUserActivity(new Intent(this,LoginActivity.class));
        }
    }
    
    private void startUserActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
}
