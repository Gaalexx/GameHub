package com.project.gamehub.di.annotations

import jakarta.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CheapSharkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SteamHttpClient


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CheapSharkRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SteamRetrofit