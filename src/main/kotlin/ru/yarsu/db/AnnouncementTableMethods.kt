package ru.yarsu.db

import org.jooq.DSLContext
import org.jooq.Record
import ru.yarsu.db.DatabaseMethods.safeLet
import ru.yarsu.db.generated.tables.references.ANNOUNCEMENTS
import ru.yarsu.domain.entities.Announcement

object AnnouncementTableMethods {
    fun DSLContext.selectAnnouncements() =
        this.select(
            ANNOUNCEMENTS.ID,
            ANNOUNCEMENTS.PUBLISHINGDATE,
            ANNOUNCEMENTS.CATEGORY,
            ANNOUNCEMENTS.TITLE,
            ANNOUNCEMENTS.DESCRIPTION,
            ANNOUNCEMENTS.SPECIALIST,
        ).from(ANNOUNCEMENTS)

    fun DSLContext.insertAnnouncement(announcement: Announcement): Announcement? =
        this.insertInto(ANNOUNCEMENTS)
            .set(ANNOUNCEMENTS.ID, announcement.id)
            .set(ANNOUNCEMENTS.PUBLISHINGDATE, announcement.date)
            .set(ANNOUNCEMENTS.CATEGORY, announcement.category)
            .set(ANNOUNCEMENTS.TITLE, announcement.title)
            .set(ANNOUNCEMENTS.DESCRIPTION, announcement.description)
            .set(ANNOUNCEMENTS.SPECIALIST, announcement.specialist)
            .returningResult()
            .fetchOne()
            ?.toAnnouncement()

    fun Record.toAnnouncement(): Announcement? =
        safeLet(
            this[ANNOUNCEMENTS.ID],
            this[ANNOUNCEMENTS.PUBLISHINGDATE],
            this[ANNOUNCEMENTS.CATEGORY],
            this[ANNOUNCEMENTS.TITLE],
            this[ANNOUNCEMENTS.DESCRIPTION],
            this[ANNOUNCEMENTS.SPECIALIST],
        ) { id, date, category, title, description, specialist ->
            Announcement(
                id = id,
                date = date,
                category = category,
                title = title,
                description = description,
                specialist = specialist
            )
        }
}
