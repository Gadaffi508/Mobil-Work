package com.example.yaplacaklistesiuygulamas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotlarAdapter.OnNotClickListener {

    private EditText baslikEditText, icerikEditText; // Not başlığı ve içeriği için EditText bileşenleri
    private Button ekleButton, logoutButton; // Not ekle ve çıkış yap butonları
    private RecyclerView recyclerView; // Notları listelemek için RecyclerView
    private NotlarAdapter notlarAdapter; // RecyclerView için Adapter
    private DatabaseReference notlarRef; // Firebase Database için referans
    private FirebaseAuth auth; // Firebase Authentication için referans
    private Not not; // Not nesnesi

    private List<Not> notlar = new ArrayList<>(); // Notları saklamak için liste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Edge-to-edge ekranı etkinleştirir
        setContentView(R.layout.activity_main); // Layout dosyasını yükler

        auth = FirebaseAuth.getInstance(); // Firebase Authentication örneğini alır
        FirebaseUser user = auth.getCurrentUser(); // Mevcut kullanıcıyı alır

        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class)); // Kullanıcı oturum açmamışsa giriş ekranına yönlendirir
            finish();
            return; // `onCreate` metodunu sonlandırır
        }

        // Layout bileşenlerini bağlar
        baslikEditText = findViewById(R.id.not_basligi);
        icerikEditText = findViewById(R.id.not_içeriği);
        ekleButton = findViewById(R.id.not_ekle_btn);
        recyclerView = findViewById(R.id.your_recycler_view);
        logoutButton = findViewById(R.id.logout_button);

        notlarRef = FirebaseDatabase.getInstance().getReference(); // Firebase Database referansını alır
        notlarAdapter = new NotlarAdapter(new ArrayList<>(), this); // Adapter'i oluşturur
        // Kullanıcı oturum açmışsa notları yükler
        gorevleriYukle();
        recyclerView.setAdapter(notlarAdapter); // Adapter'i RecyclerView'a bağlar
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // RecyclerView için layout manager ayarlar

        // Butonlar için tıklama dinleyicileri ayarlar
        ekleButton.setOnClickListener(v -> notEkleGuncellet());
        logoutButton.setOnClickListener(v -> kullanıcıOturumKapat());
    }

    // Notları Firebase'den yükleyen yöntem
    void gorevleriYukle() {
        String kullanıcıId = auth.getCurrentUser().getUid(); // Mevcut kullanıcı ID'sini alır
        Query query = notlarRef.orderByChild("kullanıcıId").equalTo(kullanıcıId); // Kullanıcıya ait notları sorgular
        Log.d("MainActivity", "Sorgu başlatıldı: " + query.toString());

        // Sorgu sonuçlarını dinler
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                notlar.clear(); // Önceki notları temizler
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("MainActivity", "Veri bulundu: " + dataSnapshot.toString());
                    Not _not = dataSnapshot.getValue(Not.class); // Not nesnesini alır
                    _not.setId(dataSnapshot.getKey()); // Not ID'sini ayarlar
                    notlar.add(_not); // Notu listeye ekler
                    Log.d("MainActivity", "Not yüklendi: " + _not.getBaslik() + " - " + _not.getİcerik());
                }
                notlarAdapter.setNotlar(notlar); // Adapter'e notları ayarlar
                Log.d("MainActivity", "Toplam not sayısı: " + notlar.size());
                Toast.makeText(MainActivity.this, "Notlar yüklendi.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("MainActivity", "Veri yüklenemedi.", error.toException()); // Hata durumunda log kaydı yapar
                Toast.makeText(MainActivity.this, "Veri okuma başarısız.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Yeni not ekleyen veya mevcut notu güncelleyen yöntem
    void notEkleGuncellet() {
        String baslik = baslikEditText.getText().toString().trim(); // Başlık metnini alır
        String icerik = icerikEditText.getText().toString().trim(); // İçerik metnini alır

        FirebaseUser user = auth.getCurrentUser(); // Mevcut kullanıcıyı alır
        if (user == null) {
            Toast.makeText(this, "Kullanıcı oturumu açılmamış", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        String kullanıcıId = user.getUid(); // Kullanıcı ID'sini alır

        if (baslik.isEmpty() || icerik.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
        } else {
            if (not == null) {
                String notId = notlarRef.child("notlar").push().getKey(); // Yeni not ID'si oluşturur
                not = new Not(notId, baslik, icerik, kullanıcıId); // Yeni not oluşturur
                notlarRef.child("notlar").child(notId).setValue(not).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "İşlem başarılı", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "Not eklendi: " + not.getBaslik() + " - " + not.getİcerik());
                    KontrolleriTemizle(); // Formu temizler
                    gorevleriYukle(); // Yeni eklenen notu göstermek için notları yeniden yükler
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Not ekleme başarısız.", e);
                });
            } else {
                not.setBaslik(baslik); // Not başlığını günceller
                not.setİcerik(icerik); // Not içeriğini günceller
                notlarRef.child("notlar").child(not.getId()).setValue(not).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Güncelleme başarılı", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "Not güncellendi: " + not.getBaslik() + " - " + not.getİcerik());
                    KontrolleriTemizle(); // Formu temizler
                    gorevleriYukle(); // Güncellenen notu göstermek için notları yeniden yükler
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Güncelleme başarısız", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Not güncelleme başarısız.", e);
                });
            }
        }
    }

    // Formu temizleyen yöntem
    void KontrolleriTemizle() {
        baslikEditText.setText(""); // Başlık alanını temizler
        icerikEditText.setText(""); // İçerik alanını temizler
        not = null; // Not nesnesini sıfırlar
    }

    // Kullanıcı oturumunu kapatan yöntem
    void kullanıcıOturumKapat() {
        auth.signOut(); // Firebase'den oturumu kapatır
        startActivity(new Intent(this, LoginActivity.class)); // Giriş ekranına yönlendirir
        finish();
    }

    // Not silme işlemini gerçekleştiren yöntem
    @Override
    public void onSilme(Not not) {
        notlarRef.child("notlar").child(not.getId()).removeValue().addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Silme Başarılı", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Not silindi: " + not.getBaslik() + " - " + not.getİcerik());
            gorevleriYukle(); // Silinen notu göstermek için notları yeniden yükler
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Silme Başarısız", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Not silme başarısız.", e);
        });
    }

    // Not güncelleme işlemini başlatan yöntem
    @Override
    public void onGüncelleme(Not not) {
        this.not = not; // Güncellenen notu saklar
        baslikEditText.setText(not.getBaslik()); // Formda başlığı ayarlar
        icerikEditText.setText(not.getİcerik()); // Formda içeriği ayarlar
    }
}
