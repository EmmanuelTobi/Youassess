package com.cosmic.youassessment.ui.onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.cosmic.youassessment.MainActivity
import com.cosmic.youassessment.R

class OnboardingActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingScreen()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen() {
    val pages = listOf(
        OnboardingPage(
            title = "Track Your Expenses",
            description = "Get insights into where your money goes and make informed financial decisions.",
            imageRes = R.drawable.ic_onboarding_expenses
        ),
        OnboardingPage(
            title = "Set Savings Goals",
            description = "Whether it's for a vacation, a new car, or an emergency fund, we help you stay on track.",
            imageRes = R.drawable.ic_onboarding_savings
        ),
        OnboardingPage(
            title = "Get Financial Insights",
            description = "Access detailed reports and analytics to make better financial choices.",
            imageRes = R.drawable.ic_onboarding_insights
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            OnboardingPage(pages[page])
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
//                    startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
//                    finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF008080) // Teal color
                )
            ) {
                Text("Create an account")
            }

            TextButton(
                onClick = {
//                    startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
//                    finish()
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Already have an account? Sign in",
                    color = Color(0xFF008080) // Teal color
                )
            }
        }
    }
}

@Composable
fun OnboardingPage(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = page.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)