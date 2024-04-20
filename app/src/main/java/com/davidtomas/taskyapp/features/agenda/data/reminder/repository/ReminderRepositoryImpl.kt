package com.davidtomas.taskyapp.features.agenda.data.reminder.repository

import com.davidtomas.taskyapp.features.agenda.data.notifications.NotificationScheduler
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api.ReminderService
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository

class ReminderRepositoryImpl(
    private val reminderService: ReminderService,
    private val reminderLocalSource: ReminderLocalSource,
    private val notificationScheduler: NotificationScheduler,
) : ReminderRepository {
    override suspend fun saveReminder(
        reminderModel: ReminderModel,
        modificationType: ModificationType
    ) {
        when (modificationType) {
            ModificationType.EDIT -> reminderService.updateReminder(reminderModel)
            else -> reminderService.createReminder(reminderModel)
        }.fold(
            onSuccess = {
                reminderLocalSource.saveReminder(reminderModel)
                notificationScheduler.cancelScheduledNotificationAndPendingIntent(reminderModel)
                notificationScheduler.scheduleNotification(reminderModel)
            },
            onError = {
                reminderLocalSource.saveReminder(reminderModel, modificationType)
            }
        )
    }

    override suspend fun getReminder(reminderId: String): ReminderModel =
        reminderLocalSource.getRemindById(reminderId = reminderId)

    override suspend fun deleteReminder(reminderModel: ReminderModel) {
        reminderService.deleteReminder(reminderId = reminderModel.id)
            .fold(
                onSuccess = {
                    reminderLocalSource.deleteReminder(reminderId = reminderModel.id)
                    notificationScheduler.cancelScheduledNotificationAndPendingIntent(reminderModel)
                },
                onError = {
                    reminderLocalSource.deleteReminder(reminderId = reminderModel.id, ModificationType.DELETE)
                }
            )
    }
}