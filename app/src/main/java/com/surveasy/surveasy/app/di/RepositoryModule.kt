package com.surveasy.surveasy.app.di

import com.google.firebase.Firebase
import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.repository.FirebaseRepositoryImpl
import com.surveasy.surveasy.data.repository.PanelRepositoryImpl
import com.surveasy.surveasy.data.repository.SurveyRepositoryImpl
import com.surveasy.surveasy.domain.repository.FirebaseRepository
import com.surveasy.surveasy.domain.repository.PanelRepository
import com.surveasy.surveasy.domain.repository.SurveyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providePanelRepository(api: SurveasyApi): PanelRepository =
        PanelRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideFirebaseRepository(fb: Firebase): FirebaseRepository = FirebaseRepositoryImpl(fb)

    @Singleton
    @Provides
    fun provideSurveyRepository(api: SurveasyApi): SurveyRepository = SurveyRepositoryImpl(api)
}