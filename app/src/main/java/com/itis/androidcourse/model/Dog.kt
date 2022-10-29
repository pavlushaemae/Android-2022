package com.itis.androidcourse.model

data class Dog(
    val id: Long,
    val name: String,
    val country: String,
    val description: String,
    val photo: String
) : Item