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
                writer.append("ID,T\u00edtulo,Descripci\u00f3n,Fecha,Hora,Categor\u00eda,Ubicaci\u00f3n,Completada\n")

                for (activity in activities) {
                    writer.append(
                        "${activity.id}," +
                        "\"${activity.title.replace("\"", "\"\"")}\"," +
                        "\"${activity.description.replace("\"", "\"\"")}\"," +
                        "${activity.date}," +
                        "${activity.time ?: ""}," +
                        "${activity.category}," +
                        "\"${activity.location.replace("\"", "\"\"")}\"," +
                        "${if (activity.isCompleted) "S\u00ed" else "No"}\n"
                    )
                }
            }

            file
        } catch (_: Exception) {
            null
        }
    }
}
