package uk.ac.aber.dcs.cs31620.vocantastic.datasource

import android.app.Application
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair

class VocantasticRepository(application: Application) {
    private val wordPairDao = VocantasticRoomDatabase.getDatabase(application)!!.wordPairDao()

    suspend fun insert(wordPair: WordPair) {
        wordPairDao.insertWordPair(wordPair)
    }

    suspend fun clearWordList() {
        wordPairDao.clearWordList()
    }

    suspend fun delete(wordPair: WordPair) {
        wordPairDao.deleteWordPair(wordPair)
    }

    fun getWordList() = wordPairDao.getWordList()

    fun getAlphOrderList() = wordPairDao.getAlphOrderList()

}