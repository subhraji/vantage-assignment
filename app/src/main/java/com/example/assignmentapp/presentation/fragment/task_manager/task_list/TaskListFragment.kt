package com.example.assignmentapp.presentation.fragment.task_manager.task_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentapp.R
import com.example.assignmentapp.databinding.FragmentTaskListBinding
import com.example.assignmentapp.presentation.fragment.task_manager.create.event.CreateTaskFragmentEvent
import com.example.assignmentapp.presentation.fragment.task_manager.create.vm.CreateTaskFragmentViewModel
import com.example.assignmentapp.presentation.fragment.task_manager.task_list.adapter.TaskListAdapter
import com.example.assignmentapp.presentation.fragment.task_manager.task_list.event.TaskListFragmentEvent
import com.example.assignmentapp.presentation.fragment.task_manager.task_list.state.TaskListFragmentState
import com.example.assignmentapp.presentation.fragment.task_manager.task_list.vm.TaskListFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskListFragment : Fragment() {
    var binding_: FragmentTaskListBinding? = null
    val binding get() = binding_!!

    private val viewModel by viewModels<TaskListFragmentViewModel>()

    @Inject
    lateinit var mTaskListAdapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = FragmentTaskListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()

        swipeToDelete()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        handleState(state)
                    }
                }

                launch {
                    viewModel.addEvent(TaskListFragmentEvent.GetTasks)
                }
            }
        }

        binding.addTaskBtn.setOnClickListener {
            findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToCreateTaskFragment())
        }

        mTaskListAdapter.setOnItemClickListener {
            findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToTaskDetailsFragment(taskModel = it))
        }
    }

    private fun swipeToDelete(){
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                mTaskListAdapter.removeItem(position)

                val taskModel = mTaskListAdapter.differ.currentList[position]
                viewModel.addEvent(TaskListFragmentEvent.DeleteTask(taskModel = taskModel))
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.taskListRecycler)
        }
    }

    private fun handleState(state: TaskListFragmentState) {
        when (state) {
            is TaskListFragmentState.GetTasksResponse -> {
                handleGetTasksResponse(state)
            }

            is TaskListFragmentState.DeleteTaskResponse -> {

                Snackbar.make(binding.root, "Successfully deleted the task.", Snackbar.LENGTH_LONG).apply {
                    setAction("Ok"){
                        //viewModel.addEvent(TaskListFragmentEvent.(taskModel = taskModel))
                    }
                }.show()
            }
        }
    }

    private fun handleGetTasksResponse(state: TaskListFragmentState.GetTasksResponse) {
        if (state.response.isNotEmpty()) {
            state.response?.let {
                mTaskListAdapter.differ.submitList(it)
            }
        }
    }

    private fun setUpRecycler() {
        binding.taskListRecycler.apply {
            adapter = mTaskListAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }
}