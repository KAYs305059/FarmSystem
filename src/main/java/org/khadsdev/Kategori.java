package org.khadsdev;

public class Kategori {

    private int kategori_Id;
    private String kategori_Navn;

    public Kategori(int kategori_Id, String kategori_Navn) {
        this.kategori_Id = kategori_Id;
        this.kategori_Navn = kategori_Navn;
    }

    public int getKategori_Id() {
        return kategori_Id;
    }

    public void setKategori_Id(int kategori_Id) {
        this.kategori_Id = kategori_Id;
    }

    public String getKategori_Navn() {
        return kategori_Navn;
    }

    public void setKategori_Navn(String kategori_Navn) {
        this.kategori_Navn = kategori_Navn;
    }



}

