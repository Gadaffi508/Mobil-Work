package com.example.yaplacaklistesiuygulamas;

import android.view.LayoutInflater; // LayoutInflater sınıfı, XML layout dosyalarını View objelerine dönüştürmek için kullanılır
import android.view.View; // View sınıfı, Android'in kullanıcı arayüzü bileşenlerinin temel sınıfıdır
import android.view.ViewGroup; // ViewGroup sınıfı, diğer View bileşenlerini gruplandırmak için kullanılır
import android.widget.Button; // Button sınıfı, buton bileşenlerini temsil eder
import android.widget.TextView; // TextView sınıfı, metin görüntülemek için kullanılır

import androidx.annotation.NonNull; // @NonNull, bir parametre veya dönüş değeri null olamaz anlamına gelir
import androidx.recyclerview.widget.RecyclerView; // RecyclerView, büyük veri setlerini verimli bir şekilde göstermek için kullanılır

import java.util.List; // List arayüzü, bir dizi öğeyi tutmak için kullanılır

public class NotlarAdapter extends RecyclerView.Adapter<NotlarAdapter.NotlarViewHolder> {
    private List<Not> notlar; // Not nesnelerini tutan liste
    private OnNotClickListener listener; // Notlara tıklama olaylarını işleyen dinleyici

    // Adapter'in yapıcı metodu
    public NotlarAdapter(List<Not> _notlar, OnNotClickListener listener) {
        this.notlar = _notlar; // Notlar listesini atar
        this.listener = listener; // Dinleyiciyi atar
    }

    // Notlar listesini güncellemek için kullanılan metod
    public void setNotlar(List<Not> _notlar) {
        this.notlar = _notlar; // Yeni notlar listesini atar
        notifyDataSetChanged(); // Adapter'e verilerin değiştiğini bildirir
    }

    // ViewHolder oluşturma işlemi
    @Override
    public NotlarAdapter.NotlarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notlar_satiri, parent, false); // LayoutInflater ile XML layout dosyasını View objesine dönüştürür
        return new NotlarViewHolder(view); // Yeni bir ViewHolder oluşturur ve döner
    }

    // Veriyi ViewHolder'a bağlama işlemi
    @Override
    public void onBindViewHolder(NotlarAdapter.NotlarViewHolder holder, int position) {
        Not not = notlar.get(position); // Listedeki mevcut pozisyondaki notu alır
        holder.baslikTextView.setText(not.getBaslik()); // Notun başlığını TextView'e ayarlar
        holder.icerikTextView.setText(not.getİcerik()); // Notun içeriğini TextView'e ayarlar

        // Silme butonuna tıklama olayını ayarlar
        holder.silButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSilme(not); // Dinleyiciyi çağırarak silme işlemini başlatır
            }
        });

        // Güncelleme butonuna tıklama olayını ayarlar
        holder.guncelleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGüncelleme(not); // Dinleyiciyi çağırarak güncelleme işlemini başlatır
            }
        });
    }

    // Notlar listesinin boyutunu döner
    @Override
    public int getItemCount() {
        return notlar.size(); // Listenin boyutunu döner
    }

    // ViewHolder sınıfı, her bir öğenin görünümlerini tutar
    public class NotlarViewHolder extends RecyclerView.ViewHolder {
        private TextView baslikTextView; // Not başlığı için TextView
        private TextView icerikTextView; // Not içeriği için TextView
        private Button silButton; // Notu silmek için buton
        private Button guncelleButton; // Notu güncellemek için buton

        // ViewHolder yapıcı metodu
        public NotlarViewHolder(View itemView) {
            super(itemView); // Üst sınıfın yapıcı metodunu çağırır
            baslikTextView = itemView.findViewById(R.id.not_basligi); // XML layout'taki başlık TextView'i bağlar
            icerikTextView = itemView.findViewById(R.id.not_içeriği); // XML layout'taki içerik TextView'i bağlar
            silButton = itemView.findViewById(R.id.not_sil_btn); // XML layout'taki silme butonunu bağlar
            guncelleButton = itemView.findViewById(R.id.not_guncelle_btn); // XML layout'taki güncelleme butonunu bağlar
        }
    }

    // Dinleyici arayüzü, tıklama olaylarını işlemek için
    public interface OnNotClickListener {
        void onSilme(Not not); // Silme işlemi için metod
        void onGüncelleme(Not not); // Güncelleme işlemi için metod
    }
}
