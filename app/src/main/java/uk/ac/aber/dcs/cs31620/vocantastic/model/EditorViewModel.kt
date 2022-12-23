package uk.ac.aber.dcs.cs31620.vocantastic.model

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class EditorViewModel(application: Application) : AndroidViewModel(application) {
    private var textFile: File =
        File(application.filesDir, "file.txt") // filesDir gives path "data/data/package/files"

    //viewmodel saves state by def
    var editorText by mutableStateOf("")


    fun onStart() {
        viewModelScope.launch(Dispatchers.IO) {
            var result = ""
            if (textFile.exists()) {
                result = textFile.readText()
            }
            editorText = result
        }
    }

    fun onPause() {
        viewModelScope.launch(Dispatchers.IO) {
            textFile.writeText(editorText)
        }
    }
}