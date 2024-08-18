package com.atta.flashcardapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.atta.flashcardapp.R
import com.atta.flashcardapp.models.FlashCardEntity
import com.atta.flashcardapp.ui.components.MyTopAppBar
import com.atta.flashcardapp.ui.components.TitleSection
import com.atta.flashcardapp.ui.theme.FlashCardAppTheme
import com.atta.flashcardapp.ui.viewmodels.FlashCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: FlashCardViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.dark_blue)
        setContent {
            FlashCardAppTheme {

                var state by remember {
                    mutableStateOf(false)
                }

                Scaffold(
                    topBar = {
                        MyTopAppBar("Flash Card App")
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    containerColor = Color.White
                ) { innerPadding ->

                    Column(modifier = Modifier.padding(paddingValues = innerPadding)
                    ) {
                        TitleSection()
                        Spacer(modifier = Modifier.padding(top = 25.dp))
                        AddFlashCardScreen(viewModel){
                            startActivity(Intent(this@MainActivity,QuizActivity::class.java))
                        }
                    }

                }
            }
        }
    }



    @Composable
    fun AddFlashCardScreen(viewModel: FlashCardViewModel,onQuizScreen:()->Unit) {

        var question by remember { mutableStateOf("") }
        var answer by remember { mutableStateOf("") }
        val questionFocusRequester = remember { FocusRequester() }

        val scope = rememberCoroutineScope()
        val modifier = Modifier.fillMaxWidth().padding(top = 5.dp, start = 15.dp, end = 15.dp)

        val textFiledBorderColor=OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = colorResource(id = R.color.dark_blue),
            unfocusedBorderColor = Color.Green
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = modifier.focusRequester(questionFocusRequester),
                value = question,
                onValueChange = { question = it },
                label = { Text("Question", color = Color.Black) },
                textStyle = TextStyle(color = Color.Black),
                colors =textFiledBorderColor
            )

            OutlinedTextField(
                modifier = modifier,
                value = answer,
                onValueChange = { answer = it },
                label = { Text("Answer", color = Color.Black) },
                colors =textFiledBorderColor
            )


            Spacer(modifier = Modifier.padding(15.dp))


            Button(
                onClick = {
                    scope.launch {
                        viewModel.addFlashCard(question, answer)
                        answer=""
                        question=""
                        Toast.makeText(this@MainActivity, "Card Added", Toast.LENGTH_SHORT).show()
                        questionFocusRequester.requestFocus()
                    }
                },
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_blue))

            ) {
                Text("Add FlashCard", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Button(onClick = onQuizScreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green.copy(alpha = .5f))
            ) {
                Text("ShowQuiz Screen", color =Color.Black)
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Button(onClick = {
                scope.launch {
                    viewModel.deleteAllFlashCards()
                    Toast.makeText(this@MainActivity, "All Cards Deleted.", Toast.LENGTH_SHORT).show()
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Clear All Previous Cards", color = Color.White)
            }

        }
    }

}
