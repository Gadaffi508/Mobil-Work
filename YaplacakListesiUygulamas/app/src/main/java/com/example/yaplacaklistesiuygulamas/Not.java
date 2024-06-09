package com.example.yaplacaklistesiuygulamas;

public class Not
{
    private String id,
    baslik,
    icerik,
    kullanıcıId;

    public Not()
    {

    }

    public Not(String id, String baslik, String icerik, String kullanıcıId)
    {
        this.id = id;
        this.baslik = baslik;
        this.icerik = icerik;
        this.kullanıcıId = kullanıcıId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String _id)
    {
        this.id = _id;
    }

    public String getBaslik()
    {
        return baslik;
    }

    public void setBaslik(String _baslik)
    {
        this.baslik = _baslik;
    }

    public String getİcerik()
    {
        return icerik;
    }

    public void setİcerik(String _icerik)
    {
        this.icerik = icerik;
    }

    public String getKullanıcıId()
    {
        return icerik;
    }

    public void setKullanıcıId(String _kullanıcıId)
    {
        this.kullanıcıId = _kullanıcıId;
    }
}
