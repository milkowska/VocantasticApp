package uk.ac.aber.dcs.cs31620.vocantastic.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.vocantastic.datasource.VocantasticRepository

class WordPairViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: VocantasticRepository = VocantasticRepository(application)

    var wordList: LiveData<List<WordPair>> = loadWordList()
        private set

    fun insertWordPair(wordPair: WordPair) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(wordPair)
        }
    }

    fun clearWordList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearWordList()
        }
    }

    fun deleteWordPair(wordPair: WordPair) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(wordPair)
        }
    }

    private fun loadWordList(): LiveData<List<WordPair>> {
        return repository.getWordList()
    }

  /*  private fun loadWordPair(): LiveData<WordPair> {
        return repository.
    }*/
}