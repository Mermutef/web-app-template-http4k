/*
 * This file is generated by jOOQ.
 */
package ru.yarsu


import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl

import ru.yarsu.tables.Countries
import ru.yarsu.tables.FlywaySchemaHistory


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Public : SchemaImpl("PUBLIC", DefaultCatalog.DEFAULT_CATALOG) {
    public companion object {

        /**
         * The reference instance of <code>PUBLIC</code>
         */
        val PUBLIC: Public = Public()
    }

    /**
     * The table <code>PUBLIC.COUNTRIES</code>.
     */
    val COUNTRIES: Countries get() = Countries.COUNTRIES

    /**
     * The table <code>PUBLIC.flyway_schema_history</code>.
     */
    val FLYWAY_SCHEMA_HISTORY: FlywaySchemaHistory get() = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        Countries.COUNTRIES,
        FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY
    )
}