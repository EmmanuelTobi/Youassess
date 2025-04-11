package com.cosmic.youassessment.ui.onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cosmic.youassessment.R
import com.cosmic.youassessment.ui.theme.CapriolaFamily

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
            imageRes = R.drawable.trading_app
        ),
        OnboardingPage(
            title = "Set Savings Goals",
            description = "Whether it's for a vacation, a new car, or an emergency fund, we help you stay on track.",
            imageRes = R.drawable.growing_money
        ),
        OnboardingPage(
            title = "Get Financial Insights",
            description = "Access detailed reports and analytics to make better financial choices.",
            imageRes = R.drawable.youassessimg_one
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pages.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .weight(7f)
                        .height(6.dp)
                        .background(
                            color = if (index <= pagerState.currentPage) Color(0xFF000000) else Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        }
        
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
                Text(
                    "Create an account",
                    fontFamily = CapriolaFamily,
                )
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
                    fontFamily = CapriolaFamily,
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
        Column (
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = page.title,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 42.sp,
                    lineHeight = 40.sp,
                    fontFamily = CapriolaFamily,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = CapriolaFamily,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start
            )
        }
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier.size(400.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)