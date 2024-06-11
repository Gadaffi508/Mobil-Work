package com.example.yaplacaklistesiuygulamas;

import android.content.Intent; // Intent sınıfı, aktiviteler arası geçiş yapmak için kullanılır
import android.os.Bundle; // Bundle, aktivite durumunu saklamak ve geri yüklemek için kullanılır
import android.widget.Button; // Button sınıfı, buton bileşenlerini temsil eder
import android.widget.EditText; // EditText sınıfı, kullanıcıdan metin girdisi almak için kullanılır
import android.widget.Toast; // Toast sınıfı, kısa mesajları kullanıcıya göstermek için kullanılır

import androidx.activity.EdgeToEdge; // Edge-to-edge ekran özelliğini etkinleştirmek için kullanılır
import androidx.appcompat.app.AppCompatActivity; // AppCompatActivity, desteklenen özelliklerle aktivite sınıfını temsil eder

import com.google.firebase.auth.FirebaseAuth; // Firebase Authentication kütüphanesi

public class LoginActivity extends AppCompatActivity {

    private EditText emailText, sifreText; // Kullanıcıdan email ve şifre almak için EditText bileşenleri
    private Button loginButton, registerButton; // Giriş ve kayıt butonları
    private FirebaseAuth auth; // Firebase Authentication nesnesi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Üst sınıfın onCreate metodunu çağırır
        EdgeToEdge.enable(this); // Edge-to-edge ekranı etkinleştirir
        setContentView(R.layout.activity_login); // Layout dosyasını yükler

        // Layout bileşenlerini bağlar
        emailText = findViewById(R.id.email);
        sifreText = findViewById(R.id.sifre);
        loginButton = findViewById(R.id.giris_button);
        registerButton = findViewById(R.id.kayıt_button);

        auth = FirebaseAuth.getInstance(); // Firebase Authentication örneğini alır

        // Butonlar için tıklama dinleyicileri ayarlar
        loginButton.setOnClickListener(v -> kullanıcıGiris());
        registerButton.setOnClickListener(v -> kullanıcıKayıt());
    }

    // Kullanıcı giriş işlemini gerçekleştiren yöntem
    void kullanıcıGiris() {
        String email = emailText.getText().toString(); // Email metnini alır
        String sifre = sifreText.getText().toString(); // Şifre metnini alır

        if (!email.isEmpty() && !sifre.isEmpty()) { // Email ve şifre alanlarının boş olmadığını kontrol eder
            auth.signInWithEmailAndPassword(email, sifre).addOnCompleteListener(task -> { // Firebase ile email ve şifre ile giriş yapar
                if (task.isSuccessful()) { // Giriş işlemi başarılıysa
                    Toast.makeText(this, "Giriş İşlemi başarılı", Toast.LENGTH_SHORT).show(); // Başarılı mesajı gösterir
                    startActivity(new Intent(this, MainActivity.class)); // MainActivity'ye yönlendirir
                    finish(); // LoginActivity'yi kapatır
                } else {
                    Toast.makeText(this, "Giriş İşlemi başarısız", Toast.LENGTH_SHORT).show(); // Başarısız mesajı gösterir
                }
            });
        } else {
            Toast.makeText(this, "Lütfen alanları doldurunuz", Toast.LENGTH_SHORT).show(); // Alanlar boşsa uyarı mesajı gösterir
        }
    }

    // Kullanıcı kayıt işlemini gerçekleştiren yöntem
    void kullanıcıKayıt() {
        String email = emailText.getText().toString(); // Email metnini alır
        String sifre = sifreText.getText().toString(); // Şifre metnini alır

        if (!email.isEmpty() && !sifre.isEmpty()) { // Email ve şifre alanlarının boş olmadığını kontrol eder
            auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener(task -> { // Firebase ile email ve şifre ile yeni kullanıcı oluşturur
                if (task.isSuccessful()) { // Kayıt işlemi başarılıysa
                    Toast.makeText(this, "Kayıt İşlemi başarılı", Toast.LENGTH_SHORT).show(); // Başarılı mesajı gösterir
                    startActivity(new Intent(this, MainActivity.class)); // MainActivity'ye yönlendirir
                    finish(); // LoginActivity'yi kapatır
                } else {
                    Toast.makeText(this, "Kayıt İşlemi başarısız", Toast.LENGTH_SHORT).show(); // Başarısız mesajı gösterir
                }
            });
        } else {
            Toast.makeText(this, "Lütfen alanları doldurunuz", Toast.LENGTH_SHORT).show(); // Alanlar boşsa uyarı mesajı gösterir
        }
    }
}
