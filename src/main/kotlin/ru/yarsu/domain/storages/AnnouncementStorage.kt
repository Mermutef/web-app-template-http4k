package ru.yarsu.domain.storages

import org.jooq.DSLContext
import org.jooq.impl.DSL.max
import ru.yarsu.db.AnnouncementTableMethods.insertAnnouncement
import ru.yarsu.db.AnnouncementTableMethods.selectAnnouncements
import ru.yarsu.db.AnnouncementTableMethods.toAnnouncement
import ru.yarsu.db.generated.tables.references.ANNOUNCEMENTS
import ru.yarsu.domain.entities.Announcement

class AnnouncementStorage(
    private val context: DSLContext,
) {
    private var firstFree =
        context
            .select(max(ANNOUNCEMENTS.ID))
            .from(ANNOUNCEMENTS)
            .fetchOne()
            ?.value1()?.let { it + 1 } ?: 0

    fun get(id: Int): Announcement? = context.selectAnnouncements().where(ANNOUNCEMENTS.ID.eq(id)).fetchOne()?.toAnnouncement()

    fun getAll(): List<Announcement> = context.selectAnnouncements().fetch().mapNotNull { it.toAnnouncement() }

    fun add(announcement: Announcement): Int {
        context.insertAnnouncement(announcement.copy(id = firstFree))
        ++firstFree
        return firstFree - 1
    }

    fun edit(announcement: Announcement): Boolean {
        return context.update(
            ANNOUNCEMENTS,
        )
            .set(ANNOUNCEMENTS.TITLE, announcement.title)
            .set(ANNOUNCEMENTS.DESCRIPTION, announcement.description)
            .set(ANNOUNCEMENTS.CATEGORY, announcement.category)
            .where(ANNOUNCEMENTS.ID.eq(announcement.id))
            .returningResult()
            .fetchOne()
            ?.toAnnouncement()
            ?.let { true } ?: false
    }

    fun delete(id: Int): Boolean {
        return context
            .deleteFrom(ANNOUNCEMENTS)
            .where(ANNOUNCEMENTS.ID.eq(id))
            .returningResult()
            .fetchOne()
            ?.toAnnouncement()
            ?.let { true } ?: false
    }
}
