package ru.yarsu

import org.jooq.*
import org.jooq.impl.*
import org.jooq.impl.DSL.*
import ru.yarsu.tables.Countries
import java.sql.DriverManager

const val MAIN_CLASS = "ru.yarsu.WebApplication"

fun main() {
    // Fetch a SQL string from a jOOQ Query in order to manually execute it with another tool.
    // For simplicity reasons, we're using the API to construct case-insensitive object references, here.
    val DB_URL = "jdbc:h2:file:~/.h2Databases/testDB"
    val connection = DriverManager.getConnection(DB_URL, "sa", "")
    val create = using(connection, SQLDialect.H2)

    val query: Query = create.select<Int>(Countries.COUNTRIES.ID)
    val sql = query.sql
    val bindValues = query.bindValues
    println(query)
    println(sql)
    println(bindValues)
}
