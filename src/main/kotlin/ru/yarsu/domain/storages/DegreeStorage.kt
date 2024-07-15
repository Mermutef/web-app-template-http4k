package ru.yarsu.domain.storages

import org.jooq.DSLContext
import org.jooq.impl.DSL.max
import ru.yarsu.db.DegreeTableMethods.insertDegree
import ru.yarsu.db.DegreeTableMethods.selectDegrees
import ru.yarsu.db.DegreeTableMethods.toDegree
import ru.yarsu.db.generated.tables.references.DEGREES
import ru.yarsu.domain.entities.Degree

class DegreeStorage(
    private val context: DSLContext,
) {
    private var firstFree =
        context
            .select(max(DEGREES.ID))
            .from(DEGREES)
            .fetchOne()
            ?.value1()?.let { it + 1 } ?: 0

    fun get(id: Int): Degree? =
        context
            .selectDegrees()
            .where(DEGREES.ID.eq(id))
            .fetchOne()
            ?.toDegree()

    fun getAll(): List<Degree> =
        context
            .selectDegrees()
            .fetch()
            .mapNotNull { it.toDegree() }

    fun add(degree: Degree): Int {
        context.insertDegree(degree.copy(id = firstFree))
        ++firstFree
        return firstFree - 1
    }

    fun edit(degree: Degree): Boolean {
        return context.update(
            DEGREES,
        )
            .set(DEGREES.RU, degree.ru)
            .set(DEGREES.TYPE, degree.type)
            .where(DEGREES.ID.eq(degree.id))
            .returningResult()
            .fetchOne()
            ?.toDegree()
            ?.let { true } ?: false
    }

    fun delete(id: Int): Boolean {
        return context
            .deleteFrom(DEGREES)
            .where(DEGREES.ID.eq(id))
            .returningResult()
            .fetchOne()
            ?.toDegree()
            ?.let { true } ?: false
    }
}
