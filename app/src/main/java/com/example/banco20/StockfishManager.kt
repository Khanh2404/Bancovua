package com.example.banco20

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream

class StockfishManager(context: Context) {

    private val process: Process?
    private val stockfishPath: String = "stockfish"

    init {
        // Sao chép file Stockfish từ assets vào thư mục cache
        val inputStream: InputStream = context.assets.open(stockfishPath)
        val cacheFile = context.cacheDir.resolve(stockfishPath)
        inputStream.copyTo(cacheFile.outputStream())
        inputStream.close()

        process = Runtime.getRuntime().exec(cacheFile.absolutePath)
    }

    fun sendCommand(command: String) {
        process?.let {
            val outputStream: OutputStream = it.outputStream
            outputStream.write((command + "\n").toByteArray())
            outputStream.flush()
        }
    }

    fun getResponse(): String {
        val response = StringBuilder()
        process?.inputStream?.bufferedReader()?.useLines { lines ->
            lines.forEach { response.append(it).append("\n") }
        }
        return response.toString()
    }

    fun close() {
        process?.destroy()
    }
}
