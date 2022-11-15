package uk.ac.aber.dcs.cs31620.vocantastic.ui.words

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme

@Composable
fun AddWordScreen(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController,
    )
    { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AddWordScreenContent(
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
private fun AddWordScreenContent(
    modifier: Modifier = Modifier
) {
    val textValueNative = rememberSaveable { mutableStateOf("") }
    val textValueForeign = rememberSaveable { mutableStateOf("") }
    val maxChar = 30
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = stringResource(id = R.string.add_word_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.add_word_headerOne),
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))

        FirstWordTextField(
            modifier = Modifier,
            textValue = textValueNative.value,
            onValueChange = {
                if (it.length <= maxChar) textValueNative.value = it
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.add_word_headerTwo),
            fontSize = 16.sp,
        )

        TranslatedWordTextField(
            modifier = Modifier,
            textValue = textValueForeign.value,
            onValueChange = {
                if (it.length <= maxChar) textValueForeign.value = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        AddButton()

        Spacer(modifier = Modifier.height(2.dp))

        Image(
            modifier = Modifier
                .size(220.dp),
            painter = painterResource(id = R.drawable.add_word),
            contentDescription = stringResource(id = R.string.add_word_image),
            contentScale = ContentScale.Crop
        )

    }

}

@Composable
fun FirstWordTextField(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = textValue,
        label = {
            Text(text = stringResource(R.string.your_language))
        },
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier,
    )
}

@Composable
fun TranslatedWordTextField(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = textValue,
        label = {
            Text(text = stringResource(R.string.foreign_language))
        },
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(220.dp)
    ) {
        Text(stringResource(id = R.string.add_to_vocabulary_list))
    }
}

@Composable
@Preview
fun FirstWordTxtFieldPreview() {
    VocantasticTheme(dynamicColor = false) {
        FirstWordTextField()
    }
}

@Composable
@Preview
fun TranslatedWordTxtFieldPreview() {
    VocantasticTheme(dynamicColor = false) {
        TranslatedWordTextField()
    }
}


@Composable
@Preview
fun AddButtonPreview() {
    VocantasticTheme(dynamicColor = false) {
        AddButton()
    }
}
