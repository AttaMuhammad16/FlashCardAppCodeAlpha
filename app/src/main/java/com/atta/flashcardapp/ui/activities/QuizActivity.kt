package com.atta.flashcardapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.atta.flashcardapp.R
import com.atta.flashcardapp.models.FlashCardEntity
import com.atta.flashcardapp.ui.activities.ui.theme.FlashCardAppTheme
import com.atta.flashcardapp.ui.components.MyTopAppBar
import com.atta.flashcardapp.ui.viewmodels.FlashCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class QuizActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: FlashCardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor=ContextCompat.getColor(this@QuizActivity, R.color.dark_blue)
        viewModel.getAllFlashCards()

        setContent {
            FlashCardAppTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White), topBar = {
                    MyTopAppBar("Quiz's")
                },
                    containerColor = Color.White
                ) { innerPadding ->
                    Column (modifier = Modifier.padding(innerPadding)){

                        val list =viewModel.flashCards.collectAsState().value

                        QuizScreen(
                            flashcards = list
                        ) { score ->
                            Toast.makeText(this@QuizActivity, "Quiz finished with score: $score", Toast.LENGTH_SHORT).show()
                            finish()
                        }


                    }
                }
            }
        }
    }

    @Composable
    fun QuizScreen(flashcards: List<FlashCardEntity>, onFinish: (Int) -> Unit) {
        var currentQuestionIndex by remember { mutableIntStateOf(0) }
        var score by remember { mutableIntStateOf(0) }
        var userAnswer by remember { mutableStateOf("") }

        val textFiledBorderColor= OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = colorResource(id = R.color.dark_blue),
            unfocusedBorderColor = Color.Green
        )



        if (currentQuestionIndex < flashcards.size) {
            val flashCard = flashcards[currentQuestionIndex]
            Log.d("QuizScreen", "Question: ${flashCard.question}, Answer: ${flashCard.answer}")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Your Question: ${flashCard.question}", fontWeight = FontWeight.Bold, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    label = { Text("Write your answers", color = Color.Black) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFiledBorderColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (userAnswer.trim().equals(flashCard.answer, ignoreCase = true)) {
                            score++
                        }
                        currentQuestionIndex++
                        userAnswer = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green.copy(alpha = .5f))
                ) {
                    Text(text = "Submit Answer",color = Color.Black)
                }
            }
        } else {
            Log.d("QuizScreen", "Quiz finished. Final Score: $score")

            // Quiz finished, show the score
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Quiz Finished!",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Your Score: $score/${flashcards.size}",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 20.sp,
                )



                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { onFinish(score) }, colors = ButtonDefaults.buttonColors(containerColor =  Color.Green.copy(alpha = .5f))) {
                    Text(text = "Finish", color = Color.Black)
                }
            }
        }
    }


}
