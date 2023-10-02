package com.ask2784.schoolmanagemant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ask2784.schoolmanagemant.databinding.AddSampleBinding
import com.ask2784.schoolmanagemant.models.Student

class NewStudentAdapter : ListAdapter<Student, NewStudentAdapter.ViewHolder>(StudentCallback.newItemCallback) {

    init { submitList(emptyList()) }
    
    private var listener: OnNewAddClickListener? = null

    fun setOnDelete(listener: OnNewAddClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AddSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: AddSampleBinding) : RecyclerView.ViewHolder(binding.root) {
        init { binding.deleteAdded.setOnClickListener {
                listener?.onDelete(adapterPosition)
                }
            }
        fun bind(student: Student) { binding.student = student }
    }

    interface OnNewAddClickListener {
        fun onDelete(position: Int)
    }
}