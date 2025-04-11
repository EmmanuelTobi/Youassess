package com.cosmic.youassessment.ui.entry

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cosmic.youassessment.MainActivity
import com.cosmic.youassessment.ui.onboarding.OnboardingActivity
import com.cosmic.youassessment.ui.theme.CapriolaFamily
import com.cosmic.youassessment.ui.theme.YouassessmentTheme

class EntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YouassessmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EntryScreen(onNavigateToMain = {
                        startActivity(Intent(this, MainActivity::class.java))
                    }, onNavigateToOnboarding = {
                        startActivity(Intent(this, OnboardingActivity::class.java))
                    })
                }
            }
        }
    }
}

@Composable
fun EntryScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToOnboarding: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to YouAssessment",
            style = MaterialTheme.typography.displayLarge,
            fontFamily = CapriolaFamily
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onNavigateToMain,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Go to Main App",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = CapriolaFamily
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onNavigateToOnboarding,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Start Onboarding",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = CapriolaFamily
            )
        }
    }
}