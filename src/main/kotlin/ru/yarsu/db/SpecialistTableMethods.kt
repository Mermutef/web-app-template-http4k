package ru.yarsu.db

import org.jooq.DSLContext
import org.jooq.Record
import ru.yarsu.db.generated.tables.references.USERS
import ru.yarsu.domain.entities.Specialist

object SpecialistTableMethods {
    fun DSLContext.selectSpecialists() =
        this.select(
            USERS.ID,
            USERS.FCS,
            USERS.DEGREE,
            USERS.PHONE,
            USERS.VKID,
            USERS.LOGIN,
            USERS.PASSWORD,
            USERS.REGISTERDATE,
            USERS.PERMISSIONS,
        ).from(USERS)

    fun DSLContext.insertSpecialist(specialist: Specialist): Specialist? =
        this.insertInto(USERS)
            .set(USERS.ID, specialist.id)
            .set(USERS.FCS, specialist.fcs)
            .set(USERS.DEGREE, specialist.degree.toTypedArray())
            .set(USERS.PHONE, specialist.phone)
            .set(USERS.VKID, specialist.vkId)
            .set(USERS.LOGIN, specialist.login)
            .set(USERS.PASSWORD, specialist.password.toByteArray())
            .set(USERS.REGISTERDATE, specialist.registerDate)
            .set(USERS.PERMISSIONS, specialist.permissions)
            .returningResult()
            .fetchOne()
            ?.toSpecialist()

    fun DSLContext.updateRole(
        specialist: Specialist,
        permissions: Int,
    ): Specialist? =
        this.update(USERS)
            .set(USERS.PERMISSIONS, permissions)
            .where(USERS.ID.eq(specialist.id))
            .returningResult()
            .fetchOne()
            ?.toSpecialist()

    fun Record.toSpecialist(): Specialist? =
        DatabaseMethods.safeLet(
            this[USERS.ID],
            this[USERS.FCS],
            this[USERS.DEGREE],
            this[USERS.PHONE],
            this[USERS.VKID],
            this[USERS.LOGIN],
            this[USERS.PASSWORD],
            this[USERS.REGISTERDATE],
            this[USERS.PERMISSIONS],
        ) { id, fcs, degree, phone, vkId, login, password, registerDate, permissions ->
            Specialist(
                id = id,
                fcs = fcs,
                degree = degree.filterNotNull(),
                phone = phone,
                vkId = vkId,
                login = login,
                password = password.decodeToString(),
                registerDate = registerDate,
                permissions = permissions
            )
        }
}
