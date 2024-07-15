package ru.yarsu.domain.storages

import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.impl.DSL.max
import org.jooq.impl.QOM.Max
import ru.yarsu.db.DatabaseMethods
import ru.yarsu.db.SpecialistTableMethods.insertSpecialist
import ru.yarsu.db.SpecialistTableMethods.selectSpecialists
import ru.yarsu.db.SpecialistTableMethods.toSpecialist
import ru.yarsu.db.generated.tables.references.USERS
import ru.yarsu.domain.entities.Specialist

class SpecialistStorage(
    private val context: DSLContext,
) {
    private var firstFree =
        context
            .select(max(USERS.ID))
            .from(USERS)
            .fetchOne()
            ?.value1()?.let { it + 1 } ?: 0

    fun get(id: Int): Specialist? =
        context
            .selectSpecialists()
            .where(USERS.ID.eq(id))
            .fetchOne()
            ?.toSpecialist()

    fun getAll(): List<Specialist> =
        context
            .selectSpecialists()
            .fetch()
            .mapNotNull { it.toSpecialist() }

    fun getByLogin(login: String): Specialist? =
        context
            .selectSpecialists()
            .where(USERS.LOGIN.eq(login))
            .fetchOne()
            ?.toSpecialist()

    fun add(specialist: Specialist): Int {
        context.insertSpecialist(specialist.copy(id = firstFree))
        ++firstFree
        return firstFree - 1
    }

    fun edit(specialist: Specialist): Boolean {
        return context.update(
            USERS,
        )
            .set(USERS.FCS, specialist.fcs)
            .set(USERS.PHONE, specialist.phone)
            .set(USERS.VKID, specialist.vkId)
            .set(USERS.LOGIN, specialist.login)
            .set(USERS.PASSWORD, specialist.password.toByteArray())
            .where(USERS.ID.eq(specialist.id))
            .returningResult()
            .fetchOne()
            ?.toSpecialist()
            ?.let { true } ?: false
    }

    fun delete(id: Int): Boolean {
        return context
            .deleteFrom(USERS)
            .where(USERS.ID.eq(id))
            .returningResult()
            .fetchOne()
            ?.toSpecialist()
            ?.let { true } ?: false
    }
}
