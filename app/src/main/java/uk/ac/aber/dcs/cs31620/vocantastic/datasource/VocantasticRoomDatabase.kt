package uk.ac.aber.dcs.cs31620.vocantastic.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairDao

/**
 * Implementation of the Room Database
 */
@Database(entities = [WordPair::class], version = 1)
abstract class VocantasticRoomDatabase : RoomDatabase() {
    abstract fun wordPairDao(): WordPairDao

    companion object {
        private var instance: VocantasticRoomDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): VocantasticRoomDatabase? {
            if (instance == null) {
                instance =
                    Room.databaseBuilder<VocantasticRoomDatabase>(
                        context.applicationContext,
                        VocantasticRoomDatabase::class.java,
                        "Vocantastic_database"
                    )
                        .build()
            }
            return instance
        }
    }
}