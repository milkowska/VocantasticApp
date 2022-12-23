package uk.ac.aber.dcs.cs31620.vocantastic.model

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EditorScreen(editorViewModel: EditorViewModel = viewModel()
) {
    EditorLifecycleHandler {
        if (it == Lifecycle.Event.ON_START) {
            editorViewModel.onStart()
        }else if( it == Lifecycle.Event.ON_PAUSE) {
            editorViewModel.onPause()
        }
    }


    TextEditor(
        text = editorViewModel.editorText,
        onValueChange = {
            editorViewModel.editorText = it
        }
    )
}


@Composable
private fun TextEditor(
    text: String,
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxSize())
}