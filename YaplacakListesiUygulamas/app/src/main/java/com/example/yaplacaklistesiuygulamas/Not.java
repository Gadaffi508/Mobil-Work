package com.example.yaplacaklistesiuygulamas;

// Not sınıfı, bir notun özelliklerini ve bu özelliklerin get/set metodlarını içerir.
public class Not {

    // Özel üye değişkenleri: notun ID'si, başlığı, içeriği ve kullanıcı ID'si.
    private String id; // Notun benzersiz kimliği
    private String baslik; // Notun başlığı
    private String icerik; // Notun içeriği
    private String kullanıcıId; // Notu oluşturan kullanıcının kimliği

    // Boş yapıcı metod, Firebase gibi araçlar için gereklidir.
    public Not() {
        // Parametresiz yapıcı metod
    }

    // Parametreli yapıcı metod, yeni bir Not nesnesi oluşturmak için kullanılır.
    public Not(String id, String baslik, String icerik, String kullanıcıId) {
        this.id = id; // Gelen ID değerini id değişkenine atar
        this.baslik = baslik; // Gelen başlık değerini baslik değişkenine atar
        this.icerik = icerik; // Gelen içerik değerini icerik değişkenine atar
        this.kullanıcıId = kullanıcıId; // Gelen kullanıcı ID değerini kullanıcıId değişkenine atar
    }

    // getId metodu, notun ID'sini döndürür.
    public String getId() {
        return id;
    }

    // setId metodu, notun ID'sini ayarlamak için kullanılır.
    public void setId(String _id) {
        this.id = _id; // Gelen ID değerini id değişkenine atar
    }

    // getBaslik metodu, notun başlığını döndürür.
    public String getBaslik() {
        return baslik;
    }

    // setBaslik metodu, notun başlığını ayarlamak için kullanılır.
    public void setBaslik(String _baslik) {
        this.baslik = _baslik; // Gelen başlık değerini baslik değişkenine atar
    }

    // getİcerik metodu, notun içeriğini döndürür.
    public String getİcerik() {
        return icerik;
    }

    // setİcerik metodu, notun içeriğini ayarlamak için kullanılır.
    public void setİcerik(String _icerik) {
        this.icerik = _icerik; // Gelen içerik değerini icerik değişkenine atar
    }

    // getKullanıcıId metodu, notu oluşturan kullanıcının ID'sini döndürür.
    public String getKullanıcıId() {
        return kullanıcıId; // Düzeltildi: icerik yerine kullanıcıId döndürülüyor
    }

    // setKullanıcıId metodu, notu oluşturan kullanıcının ID'sini ayarlamak için kullanılır.
    public void setKullanıcıId(String _kullanıcıId) {
        this.kullanıcıId = _kullanıcıId; // Gelen kullanıcı ID değerini kullanıcıId değişkenine atar
    }
}
