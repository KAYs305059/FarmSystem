package org.khadsdev;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventar {

    private static ObservableList<Produkt> produktInventar = FXCollections.observableArrayList();
    private static ObservableList katogorier = FXCollections.observableArrayList();


    public static ObservableList<Produkt> getProduktInventar(){
        return produktInventar;
    }
    public static void leggTilProdukt(Produkt produkt){
        produktInventar.add(produkt);
    }


}
