package ru.yarsu.domain.storages

import org.jooq.DSLContext
import org.jooq.impl.DSL.max
import ru.yarsu.db.CategoryTableMethods.insertCategory
import ru.yarsu.db.CategoryTableMethods.selectCategories
import ru.yarsu.db.CategoryTableMethods.toCategory
import ru.yarsu.db.generated.tables.references.CATEGORIES
import ru.yarsu.domain.entities.Category

class CategoryStorage(
    private val context: DSLContext,
) {
    private var firstFree =
        context
            .select(max(CATEGORIES.ID))
            .from(CATEGORIES)
            .fetchOne()
            ?.value1()?.let { it + 1 } ?: 0

    fun get(id: Int): Category? =
        context
            .selectCategories()
            .where(CATEGORIES.ID.eq(id))
            .fetchOne()
            ?.toCategory()

    fun getAll(): List<Category> =
        context
            .selectCategories()
            .fetch()
            .mapNotNull { it.toCategory() }

    fun add(category: Category): Int {
        context.insertCategory(category.copy(id = firstFree))
        ++firstFree
        return firstFree - 1
    }

    fun edit(category: Category): Boolean {
        return context.update(
            CATEGORIES,
        )
            .set(CATEGORIES.RU, category.ru)
            .set(CATEGORIES.NEEDLICENSE, category.needLicense)
            .where(CATEGORIES.ID.eq(category.id))
            .returningResult()
            .fetchOne()
            ?.toCategory()
            ?.let { true } ?: false
    }

    fun delete(id: Int): Boolean {
        return context
            .deleteFrom(CATEGORIES)
            .where(CATEGORIES.ID.eq(id))
            .returningResult()
            .fetchOne()
            ?.toCategory()
            ?.let { true } ?: false
    }
}
