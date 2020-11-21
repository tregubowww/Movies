package ru.myuniquenickname.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent = Intent(this, MovieDetailsActivity ::class.java)
        val  textView = findViewById<TextView>(R.id.next)
         textView.setOnClickListener{startActivity(intent)}
    }
}