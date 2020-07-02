package com.limitcode.riky.kalkulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
//import javax.xml.xpath.XPathExpression
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import org.w3c.dom.Text
import java.lang.ArithmeticException
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    lateinit var txtInput: TextView
    lateinit var txtHasil: TextView

    var lastNumeric: Boolean = false

    var stateError: Boolean = false

    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInput = findViewById(R.id.textView_deskripsi)
        txtHasil = findViewById(R.id.editText_hasil)
    }

    fun onDigit(view: View){
        if (stateError) {
            txtInput.text = (view as Button).text
            stateError = false
        } else {
            txtInput.append((view as Button).text)
        }

        lastNumeric = true
    }

    fun onDel(view: View){
        if (lastNumeric && !stateError) {

            var str = txtInput.text.toString()
            if (str != null && str.length > 0) {
                str = str.substring(0, str.length - 1)
            }
            txtInput.text = str
        }

    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !stateError && !lastDot) {
            txtInput.append(".")
            lastNumeric = false
            lastDot = false
        }
    }

    fun onOperation(view: View) {
        if(lastNumeric && !stateError) {
            txtInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onClear(view: View) {
        this.txtInput.text = ""
        this.txtHasil.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if (lastNumeric && !stateError) {
            val txt = txtInput.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result =  expression.evaluate()
                val number = result.toInt();
                txtHasil.text = NumberFormat.getInstance().format(number);
                lastDot = true
            } catch (ex: ArithmeticException) {
                txtHasil.text = "error"
                stateError = true
                lastNumeric = false
            }

        }
    }



}
