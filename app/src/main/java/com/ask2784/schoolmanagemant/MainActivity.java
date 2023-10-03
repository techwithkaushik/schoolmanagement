package com.ask2784.schoolmanagemant;

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

import com.ask2784.schoolmanagemant.adapters.StudentAdapter;
import com.ask2784.schoolmanagemant.database.StudentRepo;
import com.ask2784.schoolmanagemant.databinding.ActivityMainBinding;
import com.ask2784.schoolmanagemant.models.Student;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private StudentAdapter adapter;
    private List<Student> originalList = new ArrayList<>();
    private StudentRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolBar();
        initRecyclerView();
        initDataFromDataBase();
    }
    
    Student oldStudent;
    
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
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }
                
                @Override
                public void onTextChanged(CharSequence keyword, int arg1, int arg2, int arg3) {
                    showRecyclerView(adapter.filterByInput(keyword));
                }
                
                @Override
                public void afterTextChanged(Editable arg0) {
                }
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
        }
         else if (itemId == R.id.delete_all) {
            List<Student> deletedList = new ArrayList<>(originalList);
            deleteStudent();
            Snackbar.make(binding.getRoot(), "ALL Deleted",Snackbar.LENGTH_SHORT)
                .setAction("Undo",v->{
                    for (Student st : deletedList){
                        repo.insert(st);
                    }
                })
            .show();
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

    private void deleteStudent() {
        repo.deleteAllStudent();
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
                    final Student st = (Student) data.getSerializableExtra("student");
                    repo.delete(st);
                    Snackbar.make(binding.getRoot(), st.getName() +" of Class "+st.getClassName()+ " is Deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Undo",v->{
                            repo.insert(st);
                        })
                    .show();
                };
                if(isUpdate) {
                    final Student st = (Student) data.getSerializableExtra("student");
                    repo.update(st);
                    Snackbar.make(binding.getRoot(), st.getName() + " is Updated",Snackbar.LENGTH_SHORT)
                        .setAction("Undo",v->{
                            repo.update(oldStudent);
                        })
                    .show();
                }
                if(isAdd) {
                    ArrayList<Student> newList = new ArrayList<>((ArrayList<Student>) data.getSerializableExtra("student_list"));
                    if(!newList.isEmpty()) {
                        List<Long> idList = new ArrayList<>();
                        for(int index = 0; newList.size() > index; index++) {
                            idList.add(repo.insert(newList.get(index)));
                            newList.get(index).setId(idList.get(index));
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
            }
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
