package com.cbr.ilk.camundapostgreskotlin.unhandledProcesses

import java.sql.Connection
import java.sql.ResultSet
import java.time.LocalDateTime
import com.cbr.ilk.camundapostgreskotlin.model.Record
import java.sql.Timestamp
import java.time.ZoneOffset

class GetUnhandledProcesses {
    companion object {
        fun startProcess(conn: Connection, batchSize: Int, setDataTime: LocalDateTime, processor: (Record)->Boolean) = conn.apply {
            autoCommit = false
        }.prepareStatement(
            "select * from request where processed is not null limit $batchSize for no key update skip locked",
            ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_UPDATABLE
        ).use {
            it.executeQuery()
                .use { rs ->
                    while (rs.next()) {
                        Record(
                            rs.getString("message_uuid"),
                            rs.getString("payload"),
                            rs.getTimestamp("created").toLocalDateTime(),
                            rs.getInt("message_type")
                        ).let(processor)
                            .takeIf { it }
                            ?.also {
                                rs.updateTimestamp("processed", Timestamp(setDataTime.toInstant(ZoneOffset.UTC).toEpochMilli()))
                                //TODO FIX TIME
                                rs.updateRow();
                            }

                    }
                }
        }
    }
}