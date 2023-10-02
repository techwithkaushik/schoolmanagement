package com.ask2784.schoolmanagemant.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ask2784.schoolmanagemant.databinding.StudentSampleBinding;
import com.ask2784.schoolmanagemant.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends ListAdapter<Student, StudentAdapter.ViewHolder>{

    List<Student> originalList = new ArrayList<>();
    List<Student> classByList = new ArrayList<>();
    OnClickListener listener;
    public StudentAdapter(){
        super(StudentCallback.INSTANCE.getItemCallback());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        return new ViewHolder(StudentSampleBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setStudent(getItem(position));
    }

    public void setOnItemClick(OnClickListener listener) {
    	this.listener = listener;
    }

    public void setData(List<Student> list) {
    	originalList = list;
        classByList = list;
        submitList(list);
    }

    public int filterByInput(CharSequence keyword) {
        List<Student> filteredList = new ArrayList<>();

        for (Student st : classByList){
            if(st.getName().toLowerCase().contains(keyword.toString().toLowerCase())){
                filteredList.add(st);
            }
        }

        submitList(filteredList);
        return filteredList.size();
    }

    public int filterByClass(CharSequence keyword) {
        List<Student> filteredList = new ArrayList<>();

        for (Student st : originalList){
            if(st.getClassName().contains(keyword)){
                filteredList.add(st);
            }
        }
        submitList(filteredList);
        if(classByList.isEmpty()) {
            classByList.clear();
        }
        classByList = filteredList;
        return filteredList.size();
    }

    public void clearFilter() {
        classByList = originalList;
    	submitList(originalList);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        StudentSampleBinding binding;
        public ViewHolder(StudentSampleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v->{
                    int p = getAdapterPosition();
                    listener.setOnItemClick(getItem(p));
            });
        }
    }
    
    public interface OnClickListener {
        void setOnItemClick(Student student);
    }
 }
