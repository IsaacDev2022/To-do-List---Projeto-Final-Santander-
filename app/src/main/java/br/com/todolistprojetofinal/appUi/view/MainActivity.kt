package br.com.todolistprojetofinal.appUi.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.todolistprojetofinal.R
import br.com.todolistprojetofinal.data.dataSource.TaskDataSource
import br.com.todolistprojetofinal.TaskListAdapter
import br.com.todolistprojetofinal.appUi.viewModel.AddTaskActivity
import br.com.todolistprojetofinal.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()

        insertListeners()
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            val alertDialog = MaterialAlertDialogBuilder(this)
                .setTitle("Atenção")
                .setMessage("Tem certeza de que deseja deletar esse item?")

            alertDialog.setNegativeButton(getString(R.string.dialog_negative)) { _, _ -> }

            alertDialog.setPositiveButton(getString(R.string.dialog_confirm)) { _, _ ->
                TaskDataSource.deleteTask(it)
                Toast.makeText(this, "Tarefa deletada com sucesso", Toast.LENGTH_SHORT).show()
                updateList()
            }

            alertDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) {
            updateList()
        }
    }

    private fun updateList() {
        val list = TaskDataSource.getList()
        binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
        else View.GONE

        adapter.notifyDataSetChanged()
        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}