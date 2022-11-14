package uk.ac.aber.dcs.cs31620.vocantastic.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme


@Composable
fun HomeScreen(navController: NavController) {

}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.home_screen_welcome_image),
            contentDescription = stringResource(id = R.string.welcome_image),
            contentScale = ContentScale.Crop
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        YourLanguageButton()

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(id = R.string.your_language_supporter),
            fontSize = 12.sp,

        )

    }
}

@Composable
fun YourLanguageButton(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {
    //var text by remember { mutableStateOf(TextFieldValue(""))
    OutlinedTextField(
        value = textValue,
        label = {
            Text(text = stringResource(id = R.string.your_language))
        },
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier)
}


@Preview
@Composable
fun YourLanguageBtnPreview(){
    VocantasticTheme(dynamicColor =  false) {
        YourLanguageButton()
    }
}