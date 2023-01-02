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

    /*var wordList: LiveData<List<WordPair>> = loadWordList()
        private set*/

    var wordList: LiveData<List<WordPair>> = loadOrderedList()
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
    fun updateWordPair(wordPair: WordPair) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(wordPair)
        }
    }

    private fun loadWordList(): LiveData<List<WordPair>> {
        return repository.getWordList()
    }

    private fun loadOrderedList(): LiveData<List<WordPair>> {
        return repository.getAlphOrderList()
    }

}