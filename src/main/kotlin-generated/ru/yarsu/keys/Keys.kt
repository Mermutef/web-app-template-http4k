/*
 * This file is generated by jOOQ.
 */
package ru.yarsu.keys


import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal

import ru.yarsu.tables.FlywaySchemaHistory
import ru.yarsu.tables.records.FlywaySchemaHistoryRecord



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val FLYWAY_SCHEMA_HISTORY_PK: UniqueKey<FlywaySchemaHistoryRecord> = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), arrayOf(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK), true)
