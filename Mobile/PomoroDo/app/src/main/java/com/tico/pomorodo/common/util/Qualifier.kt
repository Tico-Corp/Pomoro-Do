package com.tico.pomorodo.common.util

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccessTokenInterceptorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenInterceptorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IdTokenInterceptorClient