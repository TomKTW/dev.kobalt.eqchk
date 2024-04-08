package dev.kobalt.eqchk.android.component

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kobalt.eqchk.android.event.EventRepository
import io.ktor.client.*
import io.ktor.client.engine.android.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ComponentModule {

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
    fun provideEventRepository(@ApplicationContext context: Context, httpClient: HttpClient): EventRepository {
        return EventRepository(context, httpClient)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) { expectSuccess = false }
    }

}

