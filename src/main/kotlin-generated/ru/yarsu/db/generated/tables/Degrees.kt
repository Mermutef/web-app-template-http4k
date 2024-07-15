/*
 * This file is generated by jOOQ.
 */
package ru.yarsu.db.generated.tables

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl
import ru.yarsu.db.generated.Public
import ru.yarsu.db.generated.keys.DEGREES_PKEY
import ru.yarsu.db.generated.tables.records.DegreesRecord
import javax.annotation.processing.Generated
import kotlin.collections.Collection

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = [
        "https://www.jooq.org",
        "jOOQ version:3.19.10",
        "catalog version:1",
        "schema version:1",
    ],
    comments = "This class is generated by jOOQ",
)
@Suppress("UNCHECKED_CAST")
open class Degrees(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, DegreesRecord>?,
    parentPath: InverseForeignKey<out Record, DegreesRecord>?,
    aliased: Table<DegreesRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?,
) : TableImpl<DegreesRecord>(
        alias,
        Public.PUBLIC,
        path,
        childPath,
        parentPath,
        aliased,
        parameters,
        DSL.comment(""),
        TableOptions.table(),
        where,
    ) {
    companion object {
        /**
         * The reference instance of <code>public.degrees</code>
         */
        val DEGREES: Degrees = Degrees()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<DegreesRecord> = DegreesRecord::class.java

    /**
     * The column <code>public.degrees.id</code>.
     */
    val ID: TableField<DegreesRecord, Int?> = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>public.degrees.type</code>.
     */
    val TYPE: TableField<DegreesRecord, String?> = createField(DSL.name("type"), SQLDataType.CLOB.nullable(false), this, "")

    /**
     * The column <code>public.degrees.ru</code>.
     */
    val RU: TableField<DegreesRecord, String?> = createField(DSL.name("ru"), SQLDataType.CLOB.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<DegreesRecord>?) : this(alias, null, null, null, aliased, null, null)
    private constructor(
        alias: Name,
        aliased: Table<DegreesRecord>?,
        parameters: Array<Field<*>?>?,
    ) : this(alias, null, null, null, aliased, parameters, null)
    private constructor(
        alias: Name,
        aliased: Table<DegreesRecord>?,
        where: Condition?,
    ) : this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.degrees</code> table reference
     */
    constructor(alias: String) : this(DSL.name(alias))

    /**
     * Create an aliased <code>public.degrees</code> table reference
     */
    constructor(alias: Name) : this(alias, null)

    /**
     * Create a <code>public.degrees</code> table reference
     */
    constructor() : this(DSL.name("degrees"), null)

    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC

    override fun getPrimaryKey(): UniqueKey<DegreesRecord> = DEGREES_PKEY

    override fun `as`(alias: String): Degrees = Degrees(DSL.name(alias), this)

    override fun `as`(alias: Name): Degrees = Degrees(alias, this)

    override fun `as`(alias: Table<*>): Degrees = Degrees(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Degrees = Degrees(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Degrees = Degrees(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Degrees = Degrees(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Degrees = Degrees(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Degrees = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Degrees = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Degrees = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Degrees = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(
        @Stringly.SQL condition: String,
    ): Degrees = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(
        @Stringly.SQL condition: String,
        vararg binds: Any?,
    ): Degrees = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(
        @Stringly.SQL condition: String,
        vararg parts: QueryPart,
    ): Degrees = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Degrees = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Degrees = where(DSL.notExists(select))
}
