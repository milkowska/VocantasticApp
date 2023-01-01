package uk.ac.aber.dcs.cs31620.vocantastic.dependency_injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.aber.dcs.cs31620.vocantastic.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object App {
    @Provides
    @Singleton
    fun dataRepo(
        @ApplicationContext context: Context
    ) = Storage(context = context)

}