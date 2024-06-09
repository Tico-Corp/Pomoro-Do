package com.tico.pomorodo.ui.auth.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.iconpack.IcLogoGoogle
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Preview(showBackground = true, showSystemUi = true)

@Composable
fun LogInScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 57.dp),
            contentAlignment = Alignment.Center
        ) {
            LogInButton()
        }
    }
}

@Composable
fun LogInButton() {
    Button(
        onClick = {/*TODO*/ },
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFF747775),
                shape = RoundedCornerShape(4.dp)
            )
            .height(40.dp)
            .fillMaxWidth(),
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF1F1F1F),
            disabledContainerColor = Color.White,
            disabledContentColor = Color(0xFF1F1F1F)
        ),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = IconPack.IcLogoGoogle,
                contentDescription = "logo of google",
                modifier = Modifier.size(18.dp),
                tint = Color.Unspecified
            )
            Text(
                text = "Log in with Google",
                color = Color(0xFF1F1F1F),
                textAlign = TextAlign.Start,
                style = PomoroDoTheme.typography.robotoMedium14
            )
        }
    }
}
