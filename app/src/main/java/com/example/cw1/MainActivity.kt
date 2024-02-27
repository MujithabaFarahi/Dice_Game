package com.example.cw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow

class MainActivity : AppCompatActivity() {
    private lateinit var newGame: Button
    private lateinit var about: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGame = findViewById(R.id.newGame)
        about = findViewById(R.id.about)


        newGame.setOnClickListener(){
            val secondPage= Intent(this,MainActivity2:: class.java);
            startActivity(secondPage)
        }

        about.setOnClickListener(){
            popUp()
        }

    }

    private fun popUp(){
        val popUpView = layoutInflater.inflate(R.layout.activity_pop_up_about,null)
        val popupWindow = PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, true)

        popupWindow.showAtLocation(about, Gravity.CENTER, 0,20)

        var okButton = popUpView.findViewById<Button>(R.id.okButton)

        okButton.setOnClickListener {
            popupWindow.dismiss()
        }

    }

}