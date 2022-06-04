package com.cbr.ilk.camundapostgreskotlin.jdbc

import com.cbr.ilk.camundapostgreskotlin.delegate.ProcessDelegate
import com.cbr.ilk.camundapostgreskotlin.model.Record
import org.slf4j.LoggerFactory
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*

class JDBCRecord {
    fun printSQLException(ex: SQLException) {
        for (e in ex) {
            if (e is SQLException) {
                e.printStackTrace(System.err)
                System.err.println("Transaction is being rolled back.")
                System.err.println("SQLState: " + e.sqlState)
                System.err.println("Error Code: " + e.errorCode)
                System.err.println("Message: " + e.message)
                var t = ex.cause
                while (t != null) {
                    println("Cause: $t")
                    t = t.cause
                }
            }
        }
    }
    private val logger = LoggerFactory.getLogger(ProcessDelegate::class.java)

    fun startProcesses(): MutableList<Record>{
        println("~ PostgreSQL JDBC Connection ~")
        Class.forName("org.postgresql.Driver");
        val result: MutableList<Record> = ArrayList<Record>()
        val SQL_SELECT = "Select * from request where processed is null limit 1 for no key update skip locked"
        // auto close connection and preparedStatement
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                var flag = false
                conn.setAutoCommit(false);
                while (flag === false) {
                    val SQL1 = "SELECT * FROM request WHERE processed IS NULL"
                    val resultSet1: ResultSet = conn.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE
                    ).executeQuery(SQL1)
                    var numberOfUnprocessed = 0
                    while (resultSet1.next()) {
                        numberOfUnprocessed++
                    }
                    logger.info("numberOfUnprocessed = "+ numberOfUnprocessed)
                    if (numberOfUnprocessed!= 0) {
                        conn.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                        ).use { preparedStatement ->
                            val resultSet = preparedStatement.executeQuery(SQL_SELECT)
                            while (resultSet.next()) {
                                val message_uuid = UUID.fromString(resultSet.getString("message_uuid"))
                                val payload = resultSet.getString("payload")
                                val created = resultSet.getTimestamp("created")
                                val message_type = resultSet.getInt("message_type")
                                val obj = Record()
                                obj.message_uuid = message_uuid
                                obj.payload = payload
                                // Timestamp -> LocalDateTime
                                obj.created = created.toLocalDateTime()
                                obj.message_type = message_type
                                resultSet.updateTimestamp("processed", Timestamp(Date().time))
                                resultSet.updateRow();
                                result.add(obj)
                                conn.commit();
                                System.out.println("Transaction is commited successfully.")
                            }
                        }
                        if (conn == null) {
                            try {
                                //  roll back transaction
                                println("Transaction is being rolled back.")
                                conn?.rollback()
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                    } else {
                        flag = true
                    }
                }
            }
        } catch (e: SQLException) {
            printSQLException(e);

            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}
