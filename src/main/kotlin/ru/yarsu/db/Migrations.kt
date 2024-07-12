//package ru.yarsu.db
//
//import org.jooq.DSLContext
//import org.jooq.SQLDialect
//import org.jooq.impl.DSL
//import ru.yarsu.db.tables.*
//import java.sql.DriverManager
//
//const val DB_URL = "jdbc:h2:file:~/.h2Databases/testDB"
//
//fun dbTest(){
//    val conn = DriverManager.getConnection(DB_URL, "sa", "")
//    val context: DSLContext = DSL.using(conn, SQLDialect.H2)
//    val res = context.select().from(Category.CATEGORY).fetch()
//    val names = res.map { it.getValue(Category.CATEGORY.RU) }.toList().forEach { println(it) }
//}