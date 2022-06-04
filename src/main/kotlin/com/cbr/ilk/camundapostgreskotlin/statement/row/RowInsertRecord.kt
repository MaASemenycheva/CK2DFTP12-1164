package com.cbr.ilk.camundapostgreskotlin.statement.row

import java.math.BigDecimal
import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.*


object RowInsertRecord {
    @JvmStatic
    fun main(args: Array<String>) {
        // auto close connection and statement
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                    val row =
                        statement.executeUpdate(generateInsert(
                            UUID.fromString("9581406a-308c-45a6-9d44-b61f4e172587"),
                            "{\"payload\": 212378}".toString(),
                            0
                        ))
                    // rows affected
                    println(row)
                }
            }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateInsert(message_uuid: UUID, payload: String, message_type: Int): String {
        return "INSERT INTO request (message_uuid, payload, created, message_type) " +
                "VALUES ('" + message_uuid + "','" + payload + "','" + LocalDateTime.now() + "','" + message_type + "')"
    }
}