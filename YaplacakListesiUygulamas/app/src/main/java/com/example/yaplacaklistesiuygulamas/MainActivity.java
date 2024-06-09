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

    private EditText baslikEditText, icerikEditText;
    private Button ekleButton, logoutButton;
    private RecyclerView recyclerView;
    private NotlarAdapter notlarAdapter;
    private DatabaseReference notlarRef;
    private FirebaseAuth auth;
    private Not not;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class)); // Kullanıcı oturum açmamışsa giriş ekranına yönlendir.
            finish();
            return; // `onCreate` metodunu sonlandır.
        }

        baslikEditText = findViewById(R.id.not_basligi);
        icerikEditText = findViewById(R.id.not_içeriği);
        ekleButton = findViewById(R.id.not_ekle_btn);
        recyclerView = findViewById(R.id.notlar_recycler_view);
        logoutButton = findViewById(R.id.logout_button);

        notlarRef = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notlarAdapter = new NotlarAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(notlarAdapter);

        ekleButton.setOnClickListener(v -> notEkleGuncellet());
        logoutButton.setOnClickListener(v -> kullanıcıOturumKapat());

        gorevleriYukle(); // Kullanıcı oturum açmışsa görevleri yükle.
    }

    void gorevleriYukle() {
        String kullanıcıId = auth.getCurrentUser().getUid();
        Query query = notlarRef.child("notlar").orderByChild("kullanıcıId").equalTo(kullanıcıId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Not> notlar = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Not not = dataSnapshot.getValue(Not.class);
                    if (not != null) {
                        not.setId(dataSnapshot.getKey());
                        notlar.add(not);
                    }
                }
                notlarAdapter.setNotlar(notlar);
                Toast.makeText(MainActivity.this, "Notlar yüklendi.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("MainActivity", "Veri yüklenemedi.", error.toException());
                Toast.makeText(MainActivity.this, "Veri okuma başarısız.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void notEkleGuncellet() {
        String baslik = baslikEditText.getText().toString().trim();
        String icerik = icerikEditText.getText().toString().trim();

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Kullanıcı oturumu açılmamış", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        String kullanıcıId = user.getUid();

        if (baslik.isEmpty() || icerik.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
        } else {
            if (not == null) {
                String notId = notlarRef.child("notlar").push().getKey();
                not = new Not(notId, baslik, icerik, kullanıcıId);
                notlarRef.child("notlar").child(notId).setValue(not).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "İşlem başarılı", Toast.LENGTH_SHORT).show();
                    KontrolleriTemizle();
                    gorevleriYukle(); // Yeni eklenen notu göstermek için görevleri yeniden yükleyin
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                });
            } else {
                not.setBaslik(baslik);
                not.setİcerik(icerik);
                notlarRef.child("notlar").child(not.getId()).setValue(not).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Güncelleme başarılı", Toast.LENGTH_SHORT).show();
                    KontrolleriTemizle();
                    gorevleriYukle(); // Güncellenen notu göstermek için görevleri yeniden yükleyin
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Güncelleme başarısız", Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    void KontrolleriTemizle() {
        baslikEditText.setText("");
        icerikEditText.setText("");
        not = null;
    }

    void kullanıcıOturumKapat() {
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSilme(Not not) {
        notlarRef.child("notlar").child(not.getId()).removeValue().addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Silme Başarılı", Toast.LENGTH_SHORT).show();
            gorevleriYukle();
        }).addOnFailureListener(e -> Toast.makeText(this, "Silme Başarısız", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onGüncelleme(Not not) {
        this.not = not;
        baslikEditText.setText(not.getBaslik());
        icerikEditText.setText(not.getİcerik());
    }
}
