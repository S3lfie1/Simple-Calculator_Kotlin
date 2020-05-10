package com.mazur.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    lateinit var displayResult: TextView
    var lastClickIsNumeric: Boolean = false
    var expressionError: Boolean = false
    var lastClickIsComma: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayResult = display
    }

    fun onDigitClick(view: View) {
        displayResult.append((view as Button).text)
        lastClickIsNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (lastClickIsNumeric && !expressionError && !lastClickIsComma) {
            displayResult.append(".")
            lastClickIsNumeric = false
            lastClickIsComma = true
        }
    }

    fun onOperator(view: View) {
        if (lastClickIsNumeric && !expressionError) {
            displayResult.append((view as Button).text)
            lastClickIsNumeric = false
            lastClickIsComma = false
        }
    }

    fun onClear(view: View) {
        displayResult.text = ""
        lastClickIsNumeric = false
        expressionError = false
        lastClickIsComma = false
    }

    fun onEqualClick(view: View) {

        if (lastClickIsNumeric && !expressionError) {
            val text = displayResult.text.toString()
            val expression = ExpressionBuilder(text).build()
            try {
                val result = expression.evaluate()
                displayResult.text = result.toString()
                lastClickIsComma = true
            } catch (ex: Exception) {
                displayResult.text = "Error"
                expressionError = true
                lastClickIsNumeric = false
            }
        }

    }
}
