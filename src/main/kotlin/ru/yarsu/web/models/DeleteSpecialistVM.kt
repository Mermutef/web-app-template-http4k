﻿package ru.yarsu.web.models

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel

class DeleteSpecialistVM(
    val specialistId: Int,
    val form: WebForm?,
) : ViewModel