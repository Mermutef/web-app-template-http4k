package ru.yarsu

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ru.yarsu.tables.Person
import java.sql.DriverManager

const val DB_URL = "jdbc:h2:file:~/.h2Databases/testDB"

fun dbTest(){
    val conn = DriverManager.getConnection(DB_URL, "sa", "")
    val context: DSLContext = DSL.using(conn, SQLDialect.H2)
    val res = context.select().from(Person.PERSON).fetch()
    val names = res.map { it.getValue(Person.PERSON.NAME) }.toList().forEach { println(it) }
}