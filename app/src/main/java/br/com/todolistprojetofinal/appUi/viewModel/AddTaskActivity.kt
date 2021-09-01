package br.com.todolistprojetofinal.appUi.viewModel

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.todolistprojetofinal.data.model.Task
import br.com.todolistprojetofinal.data.dataSource.TaskDataSource
import br.com.todolistprojetofinal.databinding.ActivityAddTaskBinding
import br.com.todolistprojetofinal.format
import br.com.todolistprojetofinal.text
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let { task ->
                binding.tilTitle.text = task.title
                binding.tilDate.text = task.date
                binding.tilHour.text = task.hour
                binding.tilDescription.text = task.description
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                date = binding.tilDate.text,
                hour = binding.tilHour.text,
                description = binding.tilDescription.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            Toast.makeText(this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}