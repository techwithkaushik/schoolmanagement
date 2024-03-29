package com.ask2784.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ask2784.schoolmanagement.adapters.StudentAdapter;
import com.ask2784.schoolmanagement.database.StudentRepo;
import com.ask2784.schoolmanagement.databinding.ActivityMainBinding;
import com.ask2784.schoolmanagement.models.Student;
import com.ask2784.schoolmanagement.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    
    private ActivityMainBinding binding;
    private StudentAdapter adapter;
    private List<Student> originalList = new ArrayList<>();
    private Student oldStudent;
    private StudentRepo repo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        initToolBar();
        initRecyclerView();
        initDataFromDataBase();
        Utils.setupUI(binding.getRoot(),this);
    }
    
    private void initToolBar() {
        setSupportActionBar(binding.toolbar);
        
        binding.searchByLyt.setStartIconOnClickListener(v->{
                PopupMenu filterMenu = new PopupMenu(this,v);
                String[] filterArray = getResources().getStringArray(R.array.classes);
                for (int i = 0; filterArray.length > i; i++) {
                    filterMenu.getMenu().add(Menu.NONE,i,i,filterArray[i]);
                }
                filterMenu.setOnMenuItemClickListener(item->{
                        showRecyclerView(adapter.filterByClass(item.getTitle()));
                    return true;
                });
                filterMenu.show();
        });
        
        binding.searchBy.addTextChangedListener(new TextWatcher(){
                
                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
                
                @Override
                public void onTextChanged(CharSequence keyword, int arg1, int arg2, int arg3) {
                    showRecyclerView(adapter.filterByInput(keyword));
                }
                
                @Override
                public void afterTextChanged(Editable arg0) {}
        });
        
        binding.searchBy.setOnFocusChangeListener((v,hasFocus)->{
                if(hasFocus) binding.searchByLyt.setHint("");
                else {
                    if(!binding.searchBy.getText().toString().isEmpty()){
                        binding.searchByLyt.setHint("");
                    } else {
                        binding.searchBy.postDelayed( new Runnable(){
                                @Override
                                public void run() {
                                    binding.searchByLyt.setHint("Search");
                                }
                        },120);
                    }
                }
            });
        
        binding.getRoot().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                private int previousHeight = binding.getRoot().getHeight();
                @Override
                public boolean onPreDraw() {
                    int newHeight = binding.getRoot().getHeight();
                    if (newHeight > previousHeight) {
                        binding.searchByLyt.clearFocus();
                    }
                    previousHeight = newHeight;
                return true;
                }
            });
    }
    
    private void initRecyclerView() {
        binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter();
        binding.mainRecyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new StudentAdapter.OnClickListener(){
                @Override
                public void setOnItemClick(Student student) {
                    oldStudent = student;
                    Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                    intent.putExtra("isEdit",true);
                    intent.putExtra("student",student);
                    launcher.launch(intent);
                }
            });
    }
    
    private void initDataFromDataBase() {
        repo = new StudentRepo(getApplication());
        repo.getAllStudent().observe(this,new Observer<List<Student>>(){
            @Override
            public void onChanged(List<Student> studentList) {
                    originalList = studentList;
                    setAdapterData(studentList);
            }
        });
    }
    
    private void showRecyclerView(int totalStudent) {
        binding.totalStudent.setText(totalStudent > 1 ? "Total Students " + totalStudent : " Total Student " + totalStudent);
        binding.totalStudent.setVisibility(totalStudent > 0 ?View.VISIBLE:View.INVISIBLE);
        binding.mainRecyclerView.setVisibility(totalStudent > 0 ?View.VISIBLE:View.INVISIBLE);
        binding.emptyList.setVisibility(totalStudent > 0 ?View.INVISIBLE:View.VISIBLE);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
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
                Log.d("MainActivity",e.toString());
                e.printStackTrace();
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.add_new) {
            addNewStudent();
        } else if (itemId == R.id.filter_off) {
            setAdapterData(originalList);
        } else if (itemId == R.id.delete_all) {
            deleteAllStudent();
        } else if (itemId == R.id.logout) {
            if (mAuth.getCurrentUser() != null) mAuth.signOut();
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private void setAdapterData(List<Student> studentList) {
        adapter.setData(studentList);
        showRecyclerView(studentList.size());
    }

    private void addNewStudent() {
        Intent intent = new Intent(this,StudentActivity.class);
        intent.putExtra("isEdit",false);
        launcher.launch(intent);
    }
    
    private void addStudents(Intent data) {
        List<Student> newList = (List<Student>) data.getSerializableExtra("student_list");
        if(!newList.isEmpty()) {
            for(int index = 0; newList.size() > index; index++) {
                newList.get(index).setId(repo.insert(newList.get(index)));
            }
            Snackbar.make(binding.getRoot(), newList.size()+" are added",Snackbar.LENGTH_SHORT)
                .setAction("Undo",v->{
                    for(Student st : newList) {
                        repo.delete(st);
                    }
                })
            .show();
        }
    }
    
    private void updateStudent(Intent data) {
        final Student st = (Student) data.getSerializableExtra("student");
        repo.update(st);
        Snackbar.make(binding.getRoot(), st.getName() + " is Updated",Snackbar.LENGTH_SHORT)
            .setAction("Undo",v->{
                repo.update(oldStudent);
            })
        .show();
    }
    
    public void deleteStudent(Intent data) {
        final Student st = (Student) data.getSerializableExtra("student");
        repo.delete(st);
        Snackbar.make(binding.getRoot(), st.getName() +" of Class "+st.getClassName()+ " is Deleted",Snackbar.LENGTH_SHORT)
            .setAction("Undo",v->{
                repo.insert(st);
            })
        .show();
    }

    private void deleteAllStudent() {
        List<Student> deletedList = new ArrayList<>(originalList);
        repo.deleteAllStudent();
        Snackbar.make(binding.getRoot(), "ALL Deleted",Snackbar.LENGTH_SHORT)
            .setAction("Undo",v->{
                for (Student st : deletedList){
                    repo.insert(st);
                }
            })
        .show();
    }
    
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),this::onLaunchActivityResult);
    
    public void onLaunchActivityResult(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if(data != null) {
                boolean isAdd = data.getBooleanExtra("isAdd",false);
                boolean isUpdate = data.getBooleanExtra("isUpdate",false);
                boolean isDelete = data.getBooleanExtra("isDelete",false);
                if (isDelete) {
                    deleteStudent(data);
                };
                if(isUpdate) {
                    updateStudent(data);
                }
                if(isAdd) {
                    addStudents(data);
                }
            }
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
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
        if(auth.getCurrentUser() == null){
            startLoginActivity();
        }
    }
    
    private void startLoginActivity() {
        Intent i = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
