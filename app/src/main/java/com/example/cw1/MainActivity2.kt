package com.example.cw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat

class MainActivity2 : AppCompatActivity() {

    private lateinit var targetOkButton: Button
    private lateinit var score:EditText
    private lateinit var switchHard:SwitchCompat

    private var switchBoolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        score = findViewById(R.id.score)
        targetOkButton = findViewById(R.id.targetOkButton)
        switchHard = findViewById(R.id.switchHard)

        targetOkButton.setOnClickListener {
            val bundle = Bundle()

            if (score.text==null || score.text.isBlank()){
                bundle.putInt("target",101)
            }
            else{
                bundle.putInt("target",Integer.parseInt(score.text.toString()))
            }
            bundle.putBoolean("hardMode",switchBoolean)

            val intent = Intent(this@MainActivity2, MainActivity3::class.java)
            intent.putExtras(bundle)

            startActivity(intent)

        }

        switchHard.setOnClickListener(){
            if (switchHard.isChecked){
                switchBoolean = true
            }
        }
    }
}