package com.davidtomas.taskyapp.features.agenda.data.reminder.repository

import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api.ReminderService
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository

class ReminderRepositoryImpl(
    private val reminderService: ReminderService,
    private val reminderLocalSource: ReminderLocalSource
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
                reminderLocalSource.saveReminder(reminderModel, ModificationType.NO_MODIFICATION)
            },
            onError = {
                reminderLocalSource.saveReminder(reminderModel, modificationType)
            }
        )
    }

    override suspend fun getReminder(reminderId: String): ReminderModel =
        reminderLocalSource.getRemindById(reminderId = reminderId)

    override suspend fun deleteReminder(reminderId: String) {
        reminderService.deleteReminder(reminderId = reminderId)
            .fold(
                onSuccess = {
                    reminderLocalSource.deleteReminder(reminderId = reminderId)
                },
                onError = {
                    reminderLocalSource.deleteReminder(reminderId = reminderId, ModificationType.DELETE)
                }
            )
    }
}