package com.example.android_caculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView

    var state: Int = 1
    var op: Int = 0 // toán tử
    var op1: String = "0"
    var op2: String = "0"
    var hasDotOp1: Boolean = false  // Theo dõi dấu chấm trong toán hạng 1
    var hasDotOp2: Boolean = false  // Theo dõi dấu chấm trong toán hạng 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)
        findViewById<Button>(R.id.btnBS).setOnClickListener(this)
        findViewById<Button>(R.id.btnC).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)
        findViewById<Button>(R.id.btnX).setOnClickListener(this)
        findViewById<Button>(R.id.btnMinus).setOnClickListener(this)
        findViewById<Button>(R.id.btnDot).setOnClickListener(this)
        findViewById<Button>(R.id.btnDivide).setOnClickListener(this)
        findViewById<Button>(R.id.btnEquals).setOnClickListener(this)
        findViewById<Button>(R.id.btnPlus).setOnClickListener(this)

        textResult = findViewById(R.id.tvResult)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn0 -> addDigit("0")
            R.id.btn1 -> addDigit("1")
            R.id.btn2 -> addDigit("2")
            R.id.btn3 -> addDigit("3")
            R.id.btn4 -> addDigit("4")
            R.id.btn5 -> addDigit("5")
            R.id.btn6 -> addDigit("6")
            R.id.btn7 -> addDigit("7")
            R.id.btn8 -> addDigit("8")
            R.id.btn9 -> addDigit("9")
            R.id.btnDot -> addDot()
            R.id.btnPlus -> {
                state = 2
                op = 1  // Phép cộng
            }
            R.id.btnMinus -> {
                state = 2
                op = 2  // Phép trừ
            }
            R.id.btnX -> {
                state = 2
                op = 3  // Phép nhân
            }
            R.id.btnDivide -> {
                state = 2
                op = 4  // Phép chia
            }
            R.id.btnEquals -> {
                state = 3
                addDigit("")  // Tính toán kết quả
            }
            R.id.btnC -> {
                clearAll()  // Xóa toàn bộ
            }
            R.id.btnCE -> {
                clearEntry()  // Xóa số hiện tại
            }
            R.id.btnBS -> {
                backspace()  // Xóa ký tự cuối
            }
        }
    }

    private fun addDot() {
        if (state == 1 && !hasDotOp1) {
            op1 += "."
            textResult.text = op1
            hasDotOp1 = true
        } else if (state == 2 && !hasDotOp2) {
            op2 += "."
            textResult.text = op2
            hasDotOp2 = true
        }
    }

    fun clearAll() {
        op1 = "0"
        op2 = "0"
        state = 1
        op = 0
        hasDotOp1 = false
        hasDotOp2 = false
        textResult.text = "0"
    }

    fun clearEntry() {
        if (state == 2) {
            op2 = "0"
            hasDotOp2 = false
        } else {
            op1 = "0"
            hasDotOp1 = false
        }
        textResult.text = "0"
    }

    fun backspace() {
        if (state == 1) {
            if (op1.isNotEmpty()) {
                op1 = op1.dropLast(1)
            }
            if (op1.isEmpty()) {
                op1 = "0"
            }
            textResult.text = op1
        } else if (state == 2) {
            if (op2.isNotEmpty()) {
                op2 = op2.dropLast(1)
            }
            if (op2.isEmpty()) {
                op2 = "0"
            }
            textResult.text = op2
        }
    }

    private fun addDigit(digit: String) {
        if (state == 1) {
            if (op1 == "0" && digit != ".") {
                op1 = digit
            } else {
                op1 += digit
            }
            textResult.text = op1
        } else if (state == 2) {
            if (op2 == "0" && digit != ".") {
                op2 = digit
            } else {
                op2 += digit
            }
            textResult.text = op2
        } else {
            var resultNumber = 0.0
            when (op) {
                1 -> resultNumber = op1.toDouble() + op2.toDouble()
                2 -> resultNumber = op1.toDouble() - op2.toDouble()
                3 -> resultNumber = op1.toDouble() * op2.toDouble()
                4 -> resultNumber = op1.toDouble() / op2.toDouble()
            }
            textResult.text = formatNumber(resultNumber)
            state = 1
            op1 = resultNumber.toString()
            op2 = "0"
            hasDotOp1 = false
            hasDotOp2 = false
        }
    }

    private fun formatNumber(num: Double): String {
        return if (num == num.toInt().toDouble()) {
            num.toInt().toString()
        } else {
            num.toString()
        }
    }
}
