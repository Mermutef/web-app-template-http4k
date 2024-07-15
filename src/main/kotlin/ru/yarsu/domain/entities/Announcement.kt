package ru.yarsu.domain.entities

import java.time.LocalDateTime
import kotlin.math.min

const val MAX_DESCRIPTION_LENGTH = 200

data class Announcement(
    val id: Int,
    val date: LocalDateTime,
    val category: Int,
    val title: String,
    val description: String,
    val specialist: Int,
) {
    fun trimDescription(): String =
        description.substring(
            0,
            min(MAX_DESCRIPTION_LENGTH, description.length),
        ) + if (description.length > MAX_DESCRIPTION_LENGTH) "..." else ""

    fun htmlDescription(): List<String> = description.split("\n")
}
