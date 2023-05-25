package com.example.pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn: Button = findViewById(R.id.btnCadastro)
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
                    //chama a funcao de cadastrar usuario
                    CriarUsuario(email, password)
                }else{
                    Toast.makeText(baseContext, "Senha deve conter no mínimo 6 caracateres", Toast.LENGTH_SHORT).show()
                }
            }

        }

        var btnLogin: Button = findViewById(R.id.btnIrParaLogin)
        btnLogin.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

    }

    fun CriarUsuario(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var user : FirebaseUser? = auth.currentUser
                    var idUsuario:String = user!!.uid
                    cadastrarDados(idUsuario)

                    Toast.makeText(baseContext, "Usuário Criado com sucesso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Falha ao criar usuário",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun cadastrarDados(id: String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/cadastros/")
        val txtNomeCadastro: EditText = findViewById(R.id.usuario)
        val txtSenha: EditText = findViewById(R.id.senha)
        val novoCadastro = mapOf(
            "id" to id,
            "senha" to txtSenha.text.toString(),
            "nome" to txtNomeCadastro.text.toString()
        )

        myRef.push().setValue(novoCadastro).addOnCompleteListener{task ->
            if(task.isSuccessful) {
                Toast.makeText(baseContext, "Nome criado com sucesso", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(baseContext, "Nome inválido", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


}