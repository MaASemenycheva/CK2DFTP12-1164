package com.cbr.ilk.camundapostgreskotlin.model

import java.time.LocalDateTime

data class Record (
    var message_uuid: String,
    var payload: String,
    var created: LocalDateTime,
    var message_type: Int,
    var processed: LocalDateTime? = null,
    )
