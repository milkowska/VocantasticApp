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

    // By default, the list is loaded in an alphabetical order.
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

    private fun loadOrderedList(): LiveData<List<WordPair>> {
        return repository.getAlphOrderList()
    }

}