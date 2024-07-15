package ru.yarsu.db

import org.jooq.DSLContext
import org.jooq.Record
import ru.yarsu.db.DatabaseMethods.safeLet
import ru.yarsu.db.generated.tables.references.CATEGORIES
import ru.yarsu.domain.entities.Category

object CategoryTableMethods {
    fun DSLContext.selectCategories() =
        this.select(
            CATEGORIES.ID,
            CATEGORIES.RU,
            CATEGORIES.NEEDLICENSE,
        ).from(CATEGORIES)

    fun DSLContext.insertCategory(category: Category): Category? =
        this.insertInto(CATEGORIES)
            .set(CATEGORIES.ID, category.id)
            .set(CATEGORIES.RU, category.ru)
            .set(CATEGORIES.NEEDLICENSE, category.needLicense)
            .returningResult()
            .fetchOne()
            ?.toCategory()

    fun Record.toCategory(): Category? =
        safeLet(
            this[CATEGORIES.ID],
            this[CATEGORIES.RU],
            this[CATEGORIES.NEEDLICENSE],
        ) { id, ru, needLicense ->
            Category(id = id, ru = ru, needLicense = needLicense)
        }
}
