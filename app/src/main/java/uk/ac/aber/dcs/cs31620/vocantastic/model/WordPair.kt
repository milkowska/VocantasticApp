package uk.ac.aber.dcs.cs31620.vocantastic.model;

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull;

/**
 *  Defining a custom data type for the database.
 */
@Entity(tableName = "wordpairs")
data class WordPair(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    var id: Int = 0,
    var entryWord: String = "",
    var translatedWord: String = ""
)
