package com.github.jamowei.person.model

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val name: String,
    val age: Int,
    val mail: String,
    val height: Double
)
