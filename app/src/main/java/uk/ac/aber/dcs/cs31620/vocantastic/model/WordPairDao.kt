package uk.ac.aber.dcs.cs31620.vocantastic.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordPairDao {
    @Insert
    suspend fun insertWordPair(wordPair: WordPair)

    @Insert
    suspend fun insertMultipleWordPairs(wordPairList: List<WordPair>)

    @Update
    suspend fun updateWordPair(wordPair: WordPair)

    @Delete
    suspend fun deleteWordPair(wordPair: WordPair)

    @Query("DELETE FROM wordpairs")
    suspend fun clearWordList()

    @Query("SELECT * FROM wordpairs")
    fun getWordList(): LiveData<List<WordPair>>


}