package com.maiky.bitacora.export

import android.content.Context
import android.os.Environment
import com.maiky.bitacora.domain.model.Activity
import java.io.File
import java.io.FileWriter

object CsvExporter {

    fun exportToCsv(
        context: Context,
        activities: List<Activity>,
        date: String
    ): File? {
        return try {
            val dir = File(
                context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                "Bitacora"
            )
            dir.mkdirs()
            val file = File(dir, "bitacora_$date.csv")

            FileWriter(file).use { writer ->
                writer.append("ID,Título,Descripción,Fecha,Hora,Completada\n")

                for (activity in activities) {
                    writer.append(
                        "${activity.id}," +
                        "\"${activity.title.replace("\"", "\"\"")}\"," +
                        "\"${activity.description.replace("\"", "\"\"")}\"," +
                        "${activity.date}," +
                        "${activity.time ?: ""}," +
                        "${if (activity.isCompleted) "Sí" else "No"}\n"
                    )
                }
            }

            file
        } catch (_: Exception) {
            null
        }
    }
}
