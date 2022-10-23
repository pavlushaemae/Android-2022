package com.itis.androidcourse.model

sealed interface Item {
    data class Ad(val id: Long) : Item
}