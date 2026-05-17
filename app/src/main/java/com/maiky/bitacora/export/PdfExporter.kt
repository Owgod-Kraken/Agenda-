package com.maiky.bitacora.export

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.maiky.bitacora.domain.model.Activity
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object PdfExporter {

    fun exportToPdf(
        context: Context,
        activities: List<Activity>,
        date: String
    ): File? {
        return try {
            val document = PdfDocument()
            val pageWidth = 595
            val pageHeight = 842
            val margin = 40f
            var yPosition = margin + 30f

            var pageNumber = 1
            var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            var page = document.startPage(pageInfo)
            var canvas: Canvas = page.canvas

            val titlePaint = Paint().apply {
                color = Color.parseColor("#6750A4")
                textSize = 24f
                isFakeBoldText = true
                isAntiAlias = true
            }

            val subtitlePaint = Paint().apply {
                color = Color.GRAY
                textSize = 14f
                isAntiAlias = true
            }

            val bodyPaint = Paint().apply {
                color = Color.BLACK
                textSize = 13f
                isAntiAlias = true
            }

            val completedPaint = Paint().apply {
                color = Color.parseColor("#4CAF50")
                textSize = 12f
                isAntiAlias = true
            }

            val pendingPaint = Paint().apply {
                color = Color.parseColor("#FF9800")
                textSize = 12f
                isAntiAlias = true
            }

            val linePaint = Paint().apply {
                color = Color.LTGRAY
                strokeWidth = 1f
            }

            canvas.drawText("App Fechas", margin, yPosition, titlePaint)
            yPosition += 25f

            val formattedDate = try {
                LocalDate.parse(date).format(DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy"))
            } catch (_: Exception) {
                date
            }
            canvas.drawText("Fecha: $formattedDate", margin, yPosition, subtitlePaint)
            yPosition += 15f

            val total = activities.size
            val completed = activities.count { it.isCompleted }
            canvas.drawText(
                "Total: $total | Completadas: $completed | Pendientes: ${total - completed}",
                margin, yPosition, subtitlePaint
            )
            yPosition += 25f

            canvas.drawLine(margin, yPosition, pageWidth - margin, yPosition, linePaint)
            yPosition += 20f

            for (activity in activities) {
                if (yPosition > pageHeight - 80f) {
                    document.finishPage(page)
                    pageNumber++
                    pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                    page = document.startPage(pageInfo)
                    canvas = page.canvas
                    yPosition = margin + 20f
                }

                val status = if (activity.isCompleted) "COMPLETADA" else "PENDIENTE"
                val statusPaint = if (activity.isCompleted) completedPaint else pendingPaint

                canvas.drawText("● ${activity.title}", margin, yPosition, bodyPaint)
                canvas.drawText("[$status]", pageWidth - margin - 80f, yPosition, statusPaint)
                yPosition += 18f

                if (activity.description.isNotBlank()) {
                    canvas.drawText("  ${activity.description}", margin + 10, yPosition, subtitlePaint)
                    yPosition += 16f
                }

                if (activity.time != null) {
                    canvas.drawText("  Hora: ${activity.time}", margin + 10, yPosition, subtitlePaint)
                    yPosition += 16f
                }

                if (activity.category.isNotBlank()) {
                    canvas.drawText("  Categor\u00eda: ${activity.category}", margin + 10, yPosition, subtitlePaint)
                    yPosition += 16f
                }

                if (activity.location.isNotBlank()) {
                    canvas.drawText("  Ubicaci\u00f3n: ${activity.location}", margin + 10, yPosition, subtitlePaint)
                    yPosition += 16f
                }

                yPosition += 8f
                canvas.drawLine(margin + 10, yPosition, pageWidth - margin - 10, yPosition, linePaint)
                yPosition += 12f
            }

            document.finishPage(page)

            val dir = File(
                context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                "Bitacora"
            )
            dir.mkdirs()
            val file = File(dir, "bitacora_$date.pdf")
            FileOutputStream(file).use { document.writeTo(it) }
            document.close()

            file
        } catch (_: Exception) {
            null
        }
    }
}
