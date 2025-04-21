package com.eightbitstechnology.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eightbitstechnology.taskapp.ui.theme.TaskAppTheme


class TaskActivity : ComponentActivity() {
    //todo 1: Add viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskAppTheme {
                //todo 2: Pass viewmodel to TodoApp
                TodoApp()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp() {
    //todo 3:  Collect all tasks from viewmodel

    val tasks = remember {
        mutableStateListOf<Task>(
            Task(title = "Task 1"),
            Task(title = "Task 2"),
            Task(title = "Task 3"),
        )
    }


    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tasks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, "Add Task")
            }
        }

    ) { padding ->
        when {
            tasks == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            tasks!!.isEmpty() -> {
                EmptyState(modifier = Modifier.padding(padding))
            }

            else -> {
                TaskList(
                    tasks = tasks!!,
                    modifier = Modifier.padding(padding),
                    onTaskClick = { task -> taskToEdit = task },
                    onTaskChecked = { task ->
                        //todo 3: Update task in viewmodel
                        val index = tasks.indexOfFirst { it.id == task.id }
                        tasks[index] = task.copy(isCompleted = !task.isCompleted)
                    },
                    onDeleteClick = { task ->
                        taskToDelete = task
                        showDeleteDialog = true
                    }
                )
            }
        }

        // Add/Edit Dialog
        if (showAddDialog || taskToEdit != null) {
            AddEditTaskDialog(
                task = taskToEdit,
                onDismiss = {
                    showAddDialog = false
                    taskToEdit = null
                },
                onTaskSaved = { title ->
                    //todo 5: Save task in viewmodel

                    if (taskToEdit != null) {
                        val index = tasks.indexOfFirst { it.id == taskToEdit!!.id }
                        tasks[index] = taskToEdit!!.copy(title = title)
                    } else {
                        val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                        tasks.add(Task(id = newId, title = title))
                    }
                    showAddDialog = false
                    taskToEdit = null
                }
            )
        }

        // Delete Dialog
        if (showDeleteDialog && taskToDelete != null) {
            DeleteConfirmationDialog(
                title = taskToDelete!!.title,
                onConfirm = {
                    //todo 6:  Delete task from viewmodel
                    tasks.remove(taskToDelete)
                    showDeleteDialog = false
                    taskToDelete = null
                },
                onDismiss = {
                    showDeleteDialog = false
                    taskToDelete = null
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoApp() {
    TaskAppTheme {
        TodoApp()
    }
}


@Composable
fun TaskList(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
    onTaskClick: (Task) -> Unit,
    onTaskChecked: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            tasks,
            key = { it.id }
        ) { task ->

            TaskItem(
                modifier = Modifier.animateItem(),
                task = task,
                onTaskClick = onTaskClick,
                onTaskChecked = onTaskChecked,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onTaskChecked: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            //CheckBox
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onTaskChecked(task) }
            )


            //Title
            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = if (task.isCompleted)
                    TextDecoration.LineThrough
                else
                    TextDecoration.None
            )

            //Delete Button
            IconButton(onClick = { onDeleteClick(task) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskItem() {
    TaskAppTheme {
        TaskItem(
            task = Task(id = 1, title = "Preview Task", isCompleted = false),
            onTaskClick = {},
            onTaskChecked = {},
            onDeleteClick = {}
        )
    }
}


@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.List,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No tasks yet",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Add a task using the + button",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyState() {
    TaskAppTheme {
        EmptyState()
    }
}

@Composable
fun AddEditTaskDialog(
    task: Task?,
    onDismiss: () -> Unit,
    onTaskSaved: (String) -> Unit
) {
    var title by remember { mutableStateOf(task?.title ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (task == null) "Add Task" else "Edit Task")
        },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Task title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank()) {
                        onTaskSaved(title)
                    }
                },
                enabled = title.isNotBlank()
            ) {
                Text(if (task == null) "Add" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAddTaskDialog() {
    TaskAppTheme {
        AddEditTaskDialog(
            task = null,
            onDismiss = {},
            onTaskSaved = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditTaskDialog() {
    TaskAppTheme {
        AddEditTaskDialog(
            task = Task(id = 1, title = "Existing Task"),
            onDismiss = {},
            onTaskSaved = {}
        )
    }
}

@Composable
fun DeleteConfirmationDialog(
    title: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("⚠️Delete Task")
        },
        text = {
            Text("Are you sure you want to delete '$title'?")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDeleteDialog() {
    TaskAppTheme {
        DeleteConfirmationDialog(
            title = "Example Task",
            onConfirm = {},
            onDismiss = {}
        )
    }
}
