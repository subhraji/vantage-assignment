package com.example.assignmentapp.presentation.fragment.task_manager.task_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentapp.data.remote.model.task.TaskModel
import com.example.assignmentapp.databinding.TaskListItemLayoutBinding
import com.example.assignmentapp.utils.convertToDate
import javax.inject.Inject

class TaskListAdapter @Inject constructor() :
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    inner class ViewHolder(
        val taskListItemLayoutBinding: TaskListItemLayoutBinding
    ) : RecyclerView.ViewHolder(taskListItemLayoutBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<TaskModel>() {
        override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TaskModel,
            newItem: TaskModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.ViewHolder {
        val itemBinding = TaskListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskListAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]

        holder.taskListItemLayoutBinding.apply {
            titleTv.text = data?.title
            descriptionTv.text = data?.description
            timeTv.text = convertToDate(data?.createdAt!!, "dd/MM/yyyy hh:mm:ss")

            root.setOnClickListener {
                onItemClickListener?.let {
                    it(data)
                }
            }
        }
    }

    private var onItemClickListener: ((TaskModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (TaskModel) -> Unit) {
        onItemClickListener = listener
    }
}