package uk.ac.aber.dcs.cs31620.vocantastic.ui.list

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import uk.ac.aber.dcs.cs31620.vocantastic.model.ListViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway

@Composable
fun ViewListScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel(),
    listViewModel: ListViewModel
) {
    val wordsList by wordPairViewModel.wordList.observeAsState(listOf())

    ViewListScreen(
        navController = navController,
        wordList = wordsList,
        doDelete = { wordPair ->
            wordPairViewModel.deleteWordPair(wordPair)
        },
        doUpdate = { wordPair ->
            wordPairViewModel.updateWordPair(wordPair)
        },
        listViewModel = listViewModel
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewListScreen(
    navController: NavHostController,
    wordList: List<WordPair> = listOf(),
    doDelete: (WordPair) -> Unit = {},
    doUpdate: (WordPair) -> Unit = {},
    listViewModel: ListViewModel
) {

    TopLevelScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            val state = listViewModel.state
            var searchQuery by remember { mutableStateOf("") }
            val filteredList = wordList.filter {
                it.entryWord.contains(searchQuery.trim(), ignoreCase = true)
                        || it.translatedWord.contains(searchQuery.trim(), ignoreCase = true)
            }
            val setShowDialog = remember { mutableStateOf(false) }

            val context = LocalContext.current

            var nativeLanguageUpdate by rememberSaveable { mutableStateOf("") }
            var foreignLanguageUpdate by rememberSaveable { mutableStateOf("") }

            var isErrorInNativeTextField by remember {
                mutableStateOf(false)
            }
            var isErrorInForeignTextField by remember {
                mutableStateOf(false)
            }


            // the empty screen is displayed if the word list is empty.
            if (wordList.isEmpty()) {
                EmptyScreenContent()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {

                    Crossfade(
                        targetState = state.isSearchBarVisible,
                        animationSpec = tween(500)
                    ) {
                        if (it) {
                            SearchBar(
                                onCloseIconClick = {
                                    listViewModel.onAction(ListViewModel.UserAction.CloseIconClicked)
                                    searchQuery = ""
                                },
                                searchText = searchQuery,
                                onTextChange = {
                                    searchQuery = it
                                }
                            )
                        } else {
                            TopAppBar(
                                onSearchIconClick = {
                                    listViewModel.onAction(ListViewModel.UserAction.SearchIconClicked)
                                },
                            )
                        }
                    }

                    Divider(
                        thickness = 2.dp,
                        modifier = Modifier.padding(vertical = 15.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 5.dp)

                    ) {

                        items(filteredList) { word ->

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column()
                                {

                                    Text(
                                        text = word.entryWord,
                                        modifier = Modifier
                                            .padding(start = 8.dp, top = 16.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                    )

                                    Text(
                                        text = word.translatedWord,
                                        modifier = Modifier
                                            .padding(start = 8.dp, top = 8.dp),
                                        fontSize = 16.sp,
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.Top,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        ElevatedButton(
                                            modifier =
                                            Modifier
                                                .padding(end = 10.dp)
                                                .width(100.dp),

                                            onClick = {
                                                doDelete(
                                                    WordPair(
                                                        entryWord = word.entryWord,
                                                        translatedWord = word.translatedWord,
                                                        id = word.id
                                                    )
                                                )
                                                Toast.makeText(
                                                    context,
                                                    "Word pair has been removed.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                        {
                                            Text(
                                                text = stringResource(id = R.string.delete),
                                                fontFamily = Railway
                                            )
                                        }
// update
                                        ElevatedButton(
                                            modifier =
                                            Modifier
                                                .padding(end = 10.dp)
                                                .width(100.dp),
                                            onClick = {
                                                setShowDialog.value = true
                                            }
                                        )
                                        {
                                            Text(
                                                text = stringResource(id = R.string.update),
                                                fontFamily = Railway
                                            )
                                        }

                                        if (setShowDialog.value) {
                                            Dialog(onDismissRequest = {
                                                setShowDialog.value = false
                                            }
                                            ) {
                                                Surface(
                                                    shape = RoundedCornerShape(16.dp),
                                                    color = MaterialTheme.colorScheme.background
                                                ) {

                                                    Box(
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Column(modifier = Modifier.padding(20.dp)) {

                                                            Row(
                                                                modifier = Modifier.fillMaxWidth(),
                                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Text(
                                                                    text = "Update this word pair: ",
                                                                    fontSize = 24.sp,
                                                                    fontFamily = Railway
                                                                )

                                                                Icon(
                                                                    imageVector = Icons.Filled.Cancel,
                                                                    contentDescription = "cancel icon",
                                                                    tint = colorResource(android.R.color.darker_gray),
                                                                    modifier = Modifier
                                                                        .width(30.dp)
                                                                        .height(30.dp)
                                                                        .clickable {
                                                                            setShowDialog.value =
                                                                                false
                                                                        }
                                                                )
                                                            }

                                                            Spacer(modifier = Modifier.height(20.dp))

                                                            TextField(
                                                                modifier = Modifier
                                                                    .fillMaxWidth(),
                                                                placeholder = { Text(text = "Enter a word") },
                                                                value = nativeLanguageUpdate,
                                                                label = {
                                                                    Text(text = stringResource(R.string.your_language))
                                                                },
                                                                onValueChange = {
                                                                    nativeLanguageUpdate = it
                                                                    isErrorInNativeTextField =
                                                                        nativeLanguageUpdate.isEmpty()
                                                                },
                                                                isError = isErrorInNativeTextField
                                                            )

                                                            Spacer(modifier = Modifier.height(10.dp))

                                                            TextField(
                                                                modifier = Modifier
                                                                    .fillMaxWidth(),

                                                                placeholder = { Text(text = "Enter the translation") },
                                                                value = foreignLanguageUpdate,
                                                                label = {
                                                                    Text(text = stringResource(R.string.foreign_language))
                                                                },
                                                                onValueChange = {
                                                                    foreignLanguageUpdate =
                                                                        it
                                                                    isErrorInForeignTextField =
                                                                        foreignLanguageUpdate.isEmpty()
                                                                },
                                                                isError = isErrorInForeignTextField
                                                            )
                                                            Spacer(modifier = Modifier.height(20.dp))
                                                            Box(
                                                                modifier = Modifier.padding(
                                                                    40.dp,
                                                                    0.dp,
                                                                    40.dp,
                                                                    0.dp
                                                                )
                                                            ) {
                                                                FilledTonalButton(
                                                                    onClick = {
                                                                        if (nativeLanguageUpdate.trim()
                                                                                .isEmpty() || foreignLanguageUpdate.trim()
                                                                                .isEmpty()
                                                                        ) {
                                                                            Toast.makeText(
                                                                                context,
                                                                                "Invalid input",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                            if (nativeLanguageUpdate.trim() == "") {
                                                                                isErrorInNativeTextField =
                                                                                    true
                                                                            } else if (foreignLanguageUpdate.trim() == "") {
                                                                                isErrorInForeignTextField =
                                                                                    true
                                                                            }
                                                                        } else {
                                                                            doUpdate(
                                                                                WordPair(
                                                                                    entryWord = nativeLanguageUpdate,
                                                                                    translatedWord = foreignLanguageUpdate,
                                                                                    id = word.id
                                                                                )
                                                                            )
                                                                            Toast.makeText(
                                                                                context,
                                                                                "Word pair has been updated.",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()

                                                                            setShowDialog.value =
                                                                                false
                                                                            nativeLanguageUpdate =
                                                                                ""
                                                                            foreignLanguageUpdate =
                                                                                ""
                                                                        }
                                                                    },
                                                                    shape = RoundedCornerShape(50.dp),
                                                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                                                        .height(50.dp)
                                                                ) {
                                                                    Text(text = "Done")
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                                Divider(startIndent = 0.dp, thickness = 1.dp)

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EmptyScreenContent(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(6.dp))

        Image(
            modifier = Modifier
                .size(340.dp),
            painter = painterResource(id = R.drawable.list_empty),
            contentDescription = stringResource(id = R.string.list_empty),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Your list is empty! Go to Add\n" +
                    " tab to note a new word. ",
            lineHeight = 1.2.em,
            fontSize = 27.sp,
            modifier = modifier
        )
    }
}


@Composable
fun SearchBar(
    onCloseIconClick: () -> Unit,
    searchText: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchText,
        onValueChange = {
            onTextChange(it)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = onCloseIconClick) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Icon"
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                fontSize = 18.sp
            )
        }
    )
}

@Composable
fun TopAppBar(
    onSearchIconClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = stringResource(id = R.string.vocabulary),
            fontSize = 32.sp,
            modifier = Modifier.padding(start = 15.dp),
            fontFamily = Railway
        )

        IconButton(onClick = onSearchIconClick) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "search icon",
                modifier = Modifier.size(32.dp)

            )
        }
    }
}

