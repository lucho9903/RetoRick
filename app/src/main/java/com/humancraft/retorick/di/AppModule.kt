package com.humancraft.retorick.di

import com.humancraft.retorick.data.network.RickAndMortyApi
import com.humancraft.retorick.data.repository.CharacterRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideRickAndMortyApi(retrofit: Retrofit): RickAndMortyApi =
        retrofit.create(RickAndMortyApi::class.java)

    @Provides
    @Singleton
    fun provideCharacterRepository(api: RickAndMortyApi): CharacterRepository =
        CharacterRepository(api)
}
