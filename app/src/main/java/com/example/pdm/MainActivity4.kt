package com.example.pdm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        init()

    }

    fun init(){
        var btnVoltar: Button = findViewById(R.id.btnVoltar)
        btnVoltar.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }

    fun mudarSenha(view: View){
        val txtSenhaNova: EditText = findViewById(R.id.txtSenhaNova)
        var senha: String = txtSenhaNova.text.toString()
        if(senha!=""){
            val user = FirebaseAuth.getInstance().currentUser
            user!!.updatePassword(senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Senha redefinida", Toast.LENGTH_SHORT).show()
                        txtSenhaNova.isVisible = false
                    }
                }
        } else {
            txtSenhaNova.isVisible = true
            Toast.makeText(baseContext, "Digite a senha nova", Toast.LENGTH_SHORT).show()
        }
    }


}