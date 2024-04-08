package dev.kobalt.eqchk.android.main

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kobalt.eqchk.android.component.LocationManager
import dev.kobalt.eqchk.android.component.NotificationManager
import dev.kobalt.eqchk.android.component.Preferences
import dev.kobalt.eqchk.android.component.WorkManager
import dev.kobalt.eqchk.android.event.EventApiService
import dev.kobalt.eqchk.android.event.EventLocalCache
import dev.kobalt.eqchk.android.event.EventRepository
import io.ktor.client.*
import io.ktor.client.engine.android.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager {
        return LocationManager(context)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return NotificationManager(context)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager(context)
    }

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): Preferences {
        return Preferences(context)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) { expectSuccess = false }
    }

    @Provides
    @Singleton
    fun provideEventApiService(httpClient: HttpClient): EventApiService {
        return EventApiService(httpClient)
    }

    @Provides
    @Singleton
    fun provideEventLocalCache(database: MainDatabase): EventLocalCache {
        return EventLocalCache(database.eventDao())
    }

    @Provides
    @Singleton
    fun provideEventRepository(apiService: EventApiService, localCache: EventLocalCache): EventRepository {
        return EventRepository(apiService, localCache)
    }

    @Singleton
    @Provides
    fun provideMainDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            "main"
        ).fallbackToDestructiveMigration().build()
    }

}

