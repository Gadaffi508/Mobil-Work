package com.example.yaplacaklistesiuygulamas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, sifreEditText;

    private Button registerButton;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.email);
        sifreEditText = findViewById(R.id.sifre);

        registerButton = findViewById(R.id.kayıt_button);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String sifre = sifreEditText.getText().toString();

            kullanıcıKayıt(email,sifre);
        });
    }

    void kullanıcıKayıt(String email, String sifre)
    {
        if(!email.isEmpty() && !sifre.isEmpty())
        {
            auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    Toast.makeText(this,"Kayıt İşlemi başarılı",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(this,"Kayıt İşlemi başarısız",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this,"Litfen alanları doldurunuz",Toast.LENGTH_SHORT).show();
        }
    }
}