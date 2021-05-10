package org.khadsdev;

public class Produkt {

    private int produkt_id;
    private String Produkt_Navn;
    private String Category_Navn;
    private int Antall;
    private String Beskrivelse;

    public Produkt(int produkt_id, String Produkt_Navn, String Category_Navn, int Antall, String Beskrivelse) {
        this.produkt_id = produkt_id;
        this.Produkt_Navn = Produkt_Navn;
        this.Category_Navn = Category_Navn;
        this.Antall = Antall;
        this.Beskrivelse = Beskrivelse;
    }

    public int getProdukt_id() {
        return produkt_id;
    }

    public void setProdukt_id(int produkt_id) {
        this.produkt_id = produkt_id;
    }

    public String getProdukt_Navn() {
        return Produkt_Navn;
    }

    public void setProdukt_Navn(String Product_Name) {
        this.Produkt_Navn = Product_Name;
    }

    public String getCategory_Navn() {
        return Category_Navn;
    }

    public void setCategory_Navn(String Category_Name) {
        this.Category_Navn = Category_Name;
    }

    public int getAntall() {
        return Antall;
    }

    public void setAntall(int Quantity) {
        this.Antall = Quantity;
    }

    public String getBeskrivelse() {
        return Beskrivelse;
    }

    public void setBeskrivelse(String Description) {
        this.Beskrivelse = Description;
    }


}
