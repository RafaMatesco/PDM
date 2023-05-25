package com.example.pdm

import android.content.Intent
import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var btn: Button = findViewById(R.id.btnLogin)
        btn.setOnClickListener{
            auth = FirebaseAuth.getInstance()

            //Salva os dados digitados
            var usuario: EditText = findViewById(R.id.usuario)
            var senha: EditText = findViewById(R.id.senha)

            //verifica se as alguma edittext esta vazia
            var email: String = usuario.text.toString()
            var password: String = senha.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(baseContext, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
            }else {
                if(password.length > 5) {
                    //chama a funcao de logar usuario
                    VerificarUsuario(email, password)
                }else{
                    Toast.makeText(baseContext, "Senha deve conter no mínimo 6 caracateres", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun VerificarUsuario(email:String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Logado com sucesso.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Usuario ou senha incorretos.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}