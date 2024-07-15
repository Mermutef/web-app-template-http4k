package ru.yarsu.db

import org.jooq.DSLContext
import org.jooq.Record
import ru.yarsu.db.DatabaseMethods.safeLet
import ru.yarsu.db.generated.tables.references.DEGREES
import ru.yarsu.domain.entities.Degree

object DegreeTableMethods {
    fun DSLContext.selectDegrees() =
        this.select(
            DEGREES.ID,
            DEGREES.RU,
            DEGREES.TYPE,
        ).from(DEGREES)

    fun DSLContext.insertDegree(degree: Degree): Degree? =
        this.insertInto(DEGREES)
            .set(DEGREES.ID, degree.id)
            .set(DEGREES.RU, degree.ru)
            .set(DEGREES.TYPE, degree.type)
            .returningResult()
            .fetchOne()
            ?.toDegree()

    fun Record.toDegree(): Degree? =
        safeLet(
            this[DEGREES.ID],
            this[DEGREES.RU],
            this[DEGREES.TYPE],
        ) { id, ru, type ->
            Degree(id = id, ru = ru, type = type)
        }
}
