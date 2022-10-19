package com.github.jamowei.common.client

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
open class ServiceResponse<T : Any>(
    open val severity: Severity,
    open val message: String,
    val content: T? = null,
) {
    companion object {
        val defaultJson = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }

        @OptIn(InternalSerializationApi::class)
        inline fun <reified T : Any> toJson(
            value: ServiceResponse<T>,
            json: Json = defaultJson
        ): String = json.encodeToString(serializer(T::class.serializer()), value)

        @OptIn(InternalSerializationApi::class)
        inline fun <reified T : Any> fromJson(
            value: String,
            json: Json = defaultJson
        ): ServiceResponse<T> = json.decodeFromString(serializer(T::class.serializer()), value)
    }
}