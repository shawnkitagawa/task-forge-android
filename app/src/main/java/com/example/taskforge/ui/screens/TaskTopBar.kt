package com.example.taskforge.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskforge.Greeting
import com.example.taskforge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(modifier: Modifier = Modifier )
{
    CenterAlignedTopAppBar(
        title = {
            Row( verticalAlignment = Alignment.CenterVertically)
            {
                Image(
                    painterResource(R.drawable.list_244044),
                    contentDescription = "Task Icon",
                    modifier = Modifier.size(64.dp).padding(8.dp),
                )
                Text(stringResource(R.string.plan_of_the_day),
                    style = MaterialTheme.typography.titleLarge)
            }

        },
        modifier = modifier
    )
}


