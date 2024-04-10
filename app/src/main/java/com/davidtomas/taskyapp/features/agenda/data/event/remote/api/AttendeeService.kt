package com.davidtomas.taskyapp.features.agenda.data.event.remote.api

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel

interface AttendeeService {
    suspend fun checkAttendee(email: String): Result<AttendeeModel?, DataError.Network>
}
