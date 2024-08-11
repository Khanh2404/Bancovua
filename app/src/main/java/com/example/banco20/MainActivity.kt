package com.example.banco20

import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var stockfishManager: StockfishManager
    private val chessLogic = ChessLogic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Stockfish
        stockfishManager = StockfishManager(this)

        // Thiết lập bàn cờ
        setupBoard()

        // Gửi lệnh đến Stockfish
        stockfishManager.sendCommand("position startpos")
        stockfishManager.sendCommand("go depth 10")

        // Đọc phản hồi từ Stockfish
        val response = stockfishManager.getResponse()
        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
    }

    private fun setupBoard() {
        val chessBoard: GridLayout = findViewById(R.id.chessBoard1)
        chessBoard.rowCount = 8
        chessBoard.columnCount = 8

        // Đặt quân cờ vào bàn cờ
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val square = androidx.appcompat.widget.AppCompatImageView(this)
                val params = GridLayout.LayoutParams()
                params.width = 0
                params.height = 0
                params.rowSpec = GridLayout.spec(row, 1f)
                params.columnSpec = GridLayout.spec(col, 1f)
                square.layoutParams = params

                // Thiết lập màu sắc của ô
                square.setBackgroundColor(if ((row + col) % 2 == 0) 0xFFFFFFFF.toInt() else 0xFF000000.toInt())

                // Thêm ô vào bàn cờ
                chessBoard.addView(square)

                // Xử lý sự kiện click trên ô
                square.setOnClickListener {
                    // Xử lý di chuyển quân cờ ở đây
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stockfishManager.close()
    }
}
