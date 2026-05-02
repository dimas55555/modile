package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskScreen()
        }
    }
}

@Composable
fun TaskScreen() {

    var isDone by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }

    val title = "Вивчити мобільну розробку"
    val priority = "Високий"

    val baseColor = when (priority) {
        "Високий" -> Color.Red
        "Середній" -> Color.Yellow
        else -> Color.Green
    }

    val priorityColor = if (isDone) Color.Gray else baseColor
    val textColor = if (isDone) Color.Gray else Color.Black
    val textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(priorityColor, shape = CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = priority,
                    color = priorityColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = textColor,
                textDecoration = textDecoration
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isDone) "✅ Виконана" else "🔴 Не виконана"
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                isDone = !isDone
                count++
            }) {
                Text(
                    if (isDone)
                        "Відмінити"
                    else
                        "Позначити виконаною"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Натиснуто разів: $count")
        }
    }
}
