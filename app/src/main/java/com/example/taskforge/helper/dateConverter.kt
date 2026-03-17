package com.example.taskforge.helper

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object dateConverter {


    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // databse to Ui
    fun longTostring(deadLine: Long) : String
    {
        val date  = Instant.ofEpochMilli(deadLine)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.format(formatter)
    }


    // Ui to databse   user enter ex) 2026-03-16

    fun stringTolong(deadLine: String): Long?
    {
        return try{
            val localDate = LocalDate.parse(deadLine, formatter)
            localDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        }catch(e: Exception)
        {
            null
        }
    }


}