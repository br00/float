package com.alessandroborelli.floatapp.ui.base

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alessandroborelli.floatapp.ui.theme.FloatTheme

@Composable
fun Catalog() {
    FloatTheme {
        Surface {
            Column {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 20.dp
                ) {
                    Text(
                        text = "Primary",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    backgroundColor = MaterialTheme.colors.secondary,
                    elevation = 20.dp
                ) {
                    Text(
                        text = "Secondary",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                    elevation = 20.dp
                ) {
                    Text(
                        text = "Surface",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 20.dp
                ) {
                    Text(
                        text = "Background",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    backgroundColor = MaterialTheme.colors.error,
                    elevation = 20.dp
                ) {
                    Text(
                        text = "Error",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    elevation = 20.dp
                ) {
                    Text(
                        text = "Primary variant",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun PrevCatalog() {
    Catalog()
}