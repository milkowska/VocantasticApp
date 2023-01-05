package uk.ac.aber.dcs.cs31620.vocantastic.datasource

import android.app.Application
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair

/**
    The repository class which is a layer of abstraction between the database and the rest of the app
 */
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

    fun getAlphOrderList() = wordPairDao.getAlphOrderList()

}