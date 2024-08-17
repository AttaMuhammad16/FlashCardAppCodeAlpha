package com.atta.flashcardapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.atta.flashcardapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(title:String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 20.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(colorResource(id = R.color.dark_blue))
    )
}