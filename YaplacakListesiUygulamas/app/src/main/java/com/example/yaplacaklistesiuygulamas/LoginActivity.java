package com.example.yaplacaklistesiuygulamas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText, sifreText;
    private Button loginButton, registerButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.email);
        sifreText = findViewById(R.id.sifre);
        loginButton = findViewById(R.id.giris_button);
        registerButton = findViewById(R.id.kayıt_button);

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> kullanıcıGiris());
        registerButton.setOnClickListener(v -> kullanıcıKayıt());
    }

    void kullanıcıGiris() {
        String email = emailText.getText().toString();
        String sifre = sifreText.getText().toString();

        if (!email.isEmpty() && !sifre.isEmpty()) {
            auth.signInWithEmailAndPassword(email, sifre).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Giriş İşlemi başarılı", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Giriş İşlemi başarısız", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Lütfen alanları doldurunuz", Toast.LENGTH_SHORT).show();
        }
    }

    void kullanıcıKayıt() {
        String email = emailText.getText().toString();
        String sifre = sifreText.getText().toString();

        if (!email.isEmpty() && !sifre.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Kayıt İşlemi başarılı", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Kayıt İşlemi başarısız", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Lütfen alanları doldurunuz", Toast.LENGTH_SHORT).show();
        }
    }
}
