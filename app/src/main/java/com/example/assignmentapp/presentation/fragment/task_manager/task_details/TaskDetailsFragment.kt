package com.example.assignmentapp.presentation.fragment.task_manager.task_details

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.navigation.fragment.navArgs
import com.example.assignmentapp.data.remote.model.task.TaskModel
import com.example.assignmentapp.databinding.FragmentTaskDetailsBinding
import com.example.assignmentapp.utils.convertToDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskDetailsFragment : BottomSheetDialogFragment() {

    var binding_: FragmentTaskDetailsBinding? = null
    val binding get() = binding_!!

    private lateinit var taskModel: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = FragmentTaskDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskModel = navArgs<TaskDetailsFragmentArgs>().value.taskModel

        renderUi()
    }

    private fun renderUi(){
        binding.taskTitleTv.text = taskModel.title
        binding.taskDescriptionTv.text = taskModel.description
        binding.timeTv.text = SpannableStringBuilder()
            .bold { append("Date : ") }
            .append(convertToDate(taskModel.createdAt!!, "dd/MM/yyyy hh:mm:ss"))

        binding.doneBtn.setOnClickListener {
            dismiss()
        }
    }
}