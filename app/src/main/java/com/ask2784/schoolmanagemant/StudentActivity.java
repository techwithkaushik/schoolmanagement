package com.ask2784.schoolmanagemant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ask2784.schoolmanagemant.adapters.NewStudentAdapter;
import com.ask2784.schoolmanagemant.adapters.StudentItemDecorator;
import com.ask2784.schoolmanagemant.databinding.ActivityStudentBinding;
import com.ask2784.schoolmanagemant.models.Student;
import com.ask2784.schoolmanagemant.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;

public class StudentActivity extends AppCompatActivity {
    
    private ActivityStudentBinding binding;
    private boolean isEdit = false;
    private Student student;
    private String className = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.l2.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent data = getIntent();
        textUitls();
        if (data == null){
            setResult(RESULT_CANCELED);
            finish();
        } else {
            isEdit = data.getBooleanExtra("isEdit",false);
            if(isEdit) {
            	student = (Student) data.getSerializableExtra("student");
                getSupportActionBar().setTitle(student.getName());
                getSupportActionBar().setSubtitle(student.getClassName());
                setDataToTextEditUtils(student);
                updateStudent(student);
            } else {
                getSupportActionBar().setTitle("Add Student");
                getSupportActionBar().setSubtitle("Start add");
                addNewStudent();
                initRecyclerView();
            }
        }
    }
    
    private NewStudentAdapter adapter;

    private void initRecyclerView() {
        binding.addRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewStudentAdapter();
        binding.addRecyclerView.setAdapter(adapter);
        adapter.setOnDelete(new NewStudentAdapter.OnNewAddClickListener(){
                
                @Override
                public void onDelete(int position) {
                    ArrayList<Student> list = new ArrayList<>(adapter.getCurrentList());
                    list.remove(position);
                    getSupportActionBar().setSubtitle("Total Students: " + list.size());
                    adapter.submitList(list);
                }
            });
    }

    private void updateStudent(Student student) {
        binding.save.setOnClickListener(v->{
                String name = binding.nameEdt.getText().toString().trim();
                String fatherName = binding.fNameEdt.getText().toString().trim();
                if(className.isEmpty()) binding.classLyt.setError("Select Class");
                else {
                    binding.classLyt.setError(null);
                    student.setName(Utils.capitalizeFirstLetter(name));
                    student.setFatherName(Utils.capitalizeFirstLetter(fatherName));
                    student.setClassName(className);
                    Intent intent = new Intent();
                    intent.putExtra("student",student);
                    intent.putExtra("isUpdate",isEdit);
                    setResult(RESULT_OK,intent);
                    finish();
                }
        });
    }

    private void textUitls() {
        binding.nameEdt.addTextChangedListener(textWatcher);
        binding.fNameEdt.addTextChangedListener(textWatcher);
        
        ArrayAdapter<String> clsAdp = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.classes));
        binding.classEdt.setAdapter(clsAdp);
        binding.classEdt.setOnItemClickListener(
                (parent, view, position, id) -> {
                    binding.classLyt.setError(null);
                    className = (String) parent.getItemAtPosition(position);
                });
    }
    
    private TextWatcher textWatcher = new TextWatcher(){
        
        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
        
    
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            String name = binding.nameEdt.getText().toString().trim();
            String fatherName = binding.fNameEdt.getText().toString().trim();
            
            if(binding.nameEdt.isFocused()){
                if(name.isEmpty()) {
                	binding.nameLyt.setError("Enter Name");
                    if(isEdit) getSupportActionBar().setSubtitle("");
                } else {
                    binding.nameLyt.setError(null);
                    if(isEdit) getSupportActionBar().setSubtitle(Utils.capitalizeFirstLetter(name));
                }
            }
            if(binding.fNameEdt.isFocused()){
                if(fatherName.isEmpty()) {
                	binding.fNameLyt.setError("Enter Father Name");
                } else {
                    binding.fNameLyt.setError(null);
                }
            }
            binding.save.setEnabled(!name.isEmpty() && !fatherName.isEmpty());
        }
        
    
        @Override
        public void afterTextChanged(Editable arg0) {
        }
    };

    private void addNewStudent() {
        binding.save.setOnClickListener(v->{
                String name = binding.nameEdt.getText().toString().trim();
                String fatherName = binding.fNameEdt.getText().toString().trim();
                if(className.isEmpty()) binding.classLyt.setError("Select Class");
                else {
                    binding.classLyt.setError(null);
                    Student student = new Student(Utils.capitalizeFirstLetter(name),Utils.capitalizeFirstLetter(fatherName),className);
                    ArrayList<Student> addList = new ArrayList<>(adapter.getCurrentList());
                    addList.add(student);
                    Comparator<Student> compar = Comparator.comparing(Student::getName)
                                                .thenComparing(Comparator.comparing(Student::getFatherName))
                                                .thenComparing(Student::getClassName);
                    addList.sort(compar);
                    adapter.submitList(addList);
                    getSupportActionBar().setSubtitle("Total Students: "+addList.size());
                    clearFocus();
                    binding.nameEdt.setText("");
                    binding.fNameEdt.setText("");
                }
        });
    }

    private void clearFocus() {
        binding.fNameEdt.clearFocus();
        binding.classEdt.clearFocus();
        binding.nameEdt.requestFocus();
        binding.nameEdt.clearFocus();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu,menu);
        if(isEdit){
            MenuItem item = menu.findItem(R.id.save_all);
            item.setTitle("Delete");
            item.setIcon(getDrawable(R.drawable.ic_delete));
        }
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.save_all){
            if(isEdit) {
                // delete
            	Intent intent = new Intent();
                intent.putExtra("student",student);
                intent.putExtra("isDelete",isEdit);
                setResult(RESULT_OK,intent);
                finish();
            } else {
                // add new as list
                ArrayList<Student> addList = new ArrayList<>(adapter.getCurrentList());
                if(addList.size() > 0){
                Intent intent = new Intent();
                intent.putExtra("student_list",addList);
                intent.putExtra("isAdd",!isEdit);
                setResult(RESULT_OK,intent);
                finish();
                }else{
                    Toast.makeText(this,"Add Student First",Toast.LENGTH_LONG).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    

    private void setDataToTextEditUtils(Student student) {
        binding.nameEdt.setText(student.getName());
        binding.fNameEdt.setText(student.getFatherName());
        className = student.getClassName();
        binding.classEdt.setText(className,false);
        binding.save.setText("Update");
    }
}
