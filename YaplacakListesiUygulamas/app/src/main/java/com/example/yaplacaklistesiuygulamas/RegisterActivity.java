package com.example.yaplacaklistesiuygulamas;

import android.content.Intent; // Intent sınıfı, başka bir aktiviteye geçiş yapmak için kullanılır
import android.os.Bundle; // Bundle sınıfı, Activity'ler arası veri geçişi için kullanılır
import android.widget.Button; // Button sınıfı, buton bileşenlerini temsil eder
import android.widget.EditText; // EditText sınıfı, kullanıcıdan metin girişi almak için kullanılır
import android.widget.Toast; // Toast sınıfı, kısa mesajları kullanıcıya göstermek için kullanılır

import androidx.activity.EdgeToEdge; // EdgeToEdge sınıfı, kenardan kenara ekran desteği sağlar
import androidx.appcompat.app.AppCompatActivity; // AppCompatActivity sınıfı, modern Android özelliklerini kullanmak için temel Activity sınıfıdır

import com.google.firebase.auth.FirebaseAuth; // FirebaseAuth sınıfı, Firebase kimlik doğrulama işlemleri için kullanılır

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, sifreEditText; // Kullanıcıdan e-posta ve şifre almak için EditText alanları
    private Button registerButton; // Kayıt işlemi için buton
    private FirebaseAuth auth; // Firebase kimlik doğrulama nesnesi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Üst sınıfın onCreate metodunu çağırır
        EdgeToEdge.enable(this); // Kenardan kenara ekran desteğini etkinleştirir
        setContentView(R.layout.activity_register); // Aktivitenin layout dosyasını belirler

        emailEditText = findViewById(R.id.email); // XML layout dosyasındaki e-posta EditText'ini bağlar
        sifreEditText = findViewById(R.id.sifre); // XML layout dosyasındaki şifre EditText'ini bağlar
        registerButton = findViewById(R.id.kayıt_button); // XML layout dosyasındaki kayıt butonunu bağlar

        auth = FirebaseAuth.getInstance(); // Firebase kimlik doğrulama nesnesini alır

        // Kayıt butonuna tıklama olayını ayarlar
        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString(); // E-posta EditText'inden metni alır
            String sifre = sifreEditText.getText().toString(); // Şifre EditText'inden metni alır

            kullanıcıKayıt(email, sifre); // Kullanıcı kayıt metodunu çağırır
        });
    }

    // Kullanıcıyı kaydetmek için metod
    void kullanıcıKayıt(String email, String sifre) {
        if (!email.isEmpty() && !sifre.isEmpty()) { // E-posta ve şifre alanlarının boş olmadığını kontrol eder
            auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener(task -> {
                if (task.isSuccessful()) { // Kayıt işlemi başarılıysa
                    Toast.makeText(this, "Kayıt İşlemi başarılı", Toast.LENGTH_SHORT).show(); // Başarılı mesajı gösterir
                    startActivity(new Intent(this, MainActivity.class)); // MainActivity'ye geçiş yapar
                    finish(); // Bu aktiviteyi sonlandırır
                } else {
                    Toast.makeText(this, "Kayıt İşlemi başarısız", Toast.LENGTH_SHORT).show(); // Başarısız mesajı gösterir
                }
            });
        } else {
            Toast.makeText(this, "Lütfen alanları doldurunuz", Toast.LENGTH_SHORT).show(); // Boş alanlar varsa kullanıcıyı uyarır
        }
    }
}
