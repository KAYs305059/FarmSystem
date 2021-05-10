package org.khadsdev;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

    static List<Produkt> produkter = new ArrayList<Produkt>();
    static List<Kategori> kategorier = new ArrayList<Kategori>();



    @FXML
    private TextField idTxt;

    @FXML
    private TextField navnTxt;

    @FXML
    private TextField antallTxt;

    @FXML
    private TextField beskrivelseTxt;

    @FXML
    private ComboBox kategoriCbx;

    @FXML
    private ObservableList<String> kategorierListe = FXCollections.observableArrayList();


    @FXML
    private TableView<Produkt> produktTabell;

    @FXML
    private TableColumn prodId;

    @FXML
    private TableColumn prodNavn;

    @FXML
    private TableColumn prodAnt;

    @FXML
    private TableColumn prodKate;

    @FXML
    private TableColumn prodBesk;

    private ObservableList<Produkt> data;


    @FXML
    void valg() {
    }



    public void add(Produkt produkt){
        produktTabell.getItems().add(produkt);

    }



    //Hjelpe metode for tømming av felter
    private void tømProdFelt(){
        idTxt.clear();
        navnTxt.clear();
        antallTxt.clear();
        beskrivelseTxt.clear();
        kategoriCbx.setValue(null);
    }

    //Tøm alle feltene
    @FXML
    private void handleTømFelt() {
        tømProdFelt();
        produktTabell.getSelectionModel().clearSelection();
      }

    //Bytt til kategorier scenen
    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {
        Parent kategoriParent = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        Scene kategoriScene = new Scene(kategoriParent);

        //Denne linjen henter Stage infoen.
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(kategoriScene);
        window.show();
    }

    //Slett et produkt
    @FXML
    private void handleSlett() {

        int ID = Integer.parseInt(idTxt.getText());
        boolean deleted = false;

        for(Produkt p : produkter) {

            if(p.getProdukt_id() == ID) {

                produkter.remove(p);
                deleted = true;
                break;
            }
        }

        if(deleted == true) {

            System.out.println("Produkt Slettet");

            data = getInitialTableData();
            produktTabell.setItems(data);

            idTxt.setText("");
            navnTxt.setText("");
            antallTxt.setText("");
            kategoriCbx.setValue("Velg Kategori");
            beskrivelseTxt.setText("");

            produktLagreTilFil();

        } else {
            System.out.println("Produkt ikke slettet noe gikk galt");
        }
    }

    //Endre et produkt
    @FXML
    private void handleEndre() {

        String ProduktID = idTxt.getText();
        String Navn = navnTxt.getText();
        String Antall = antallTxt.getText();
        String Kategori = kategoriCbx.getValue().toString();
        String Beskrivelse = beskrivelseTxt.getText();

        Produkt produkt = new Produkt(Integer.parseInt(ProduktID), Navn, Kategori, Integer.parseInt(Antall.trim()), Beskrivelse);

        boolean updated = false;

        for(Produkt p : PrimaryController.produkter){

            if(p.getProdukt_id() == produkt.getProdukt_id()){

                PrimaryController.produkter.remove(p);
                updated = PrimaryController.produkter.add(produkt);
                updated = true;
                break;
            }
        }

        if(updated == true){

            System.out.println("Produkt Endret");

            data = getInitialTableData();
            produktTabell.setItems(data);

            idTxt.setText("");
            navnTxt.setText("");
            antallTxt.setText("");
            beskrivelseTxt.setText("");

            produktLagreTilFil();

        } else {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Feil Melding");
            alert.setHeaderText("Produkt ID kan ikke endres!!");
            alert.setContentText("Du kan endre alt annet borsett fra Produkt ID, prøv igjen!");
            alert.showAndWait();
            System.out.println("Produkt ikke endret noe gikk galt");
        }
    }

    //Legg til nytt produkt
    @FXML
    private void handleLeggTilProd() {
        try {
            if(kategoriCbx.getValue() != null) {
                String ProduktID = idTxt.getText();
                String Navn = navnTxt.getText();
                String Antall = antallTxt.getText();
                String Kategori = kategoriCbx.getValue().toString();
                String Beskrivelse = beskrivelseTxt.getText();

                Produkt product = new Produkt(Integer.parseInt(ProduktID), Navn, Kategori, Integer.parseInt(Antall), Beskrivelse);

                boolean added = produkter.add(product);

                if(added == true){

                    System.out.println("Produkt lagt til");

                    data = getInitialTableData();
                    produktTabell.setItems(data);

                    tømProdFelt();
                    produktLagreTilFil();
                }else{
                    System.out.println("Produkt ikke lagt til noe gikk feil");
                }
            } else {

                Alert alert = new Alert (Alert.AlertType.WARNING);
                alert.setTitle("Feil Melding");
                alert.setHeaderText("Kategori kan ikke være blank!!");
                alert.setContentText("Vennligst velg en kategori og prøv igjen.");
                alert.showAndWait();
                System.out.println("Du må vlge kat");
            }


        } catch (NumberFormatException e) {

            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Feil Melding");
            alert.setHeaderText("Kategori ID og Antall må være et number");
            alert.setContentText("Du skrev inn ugyldig data, vennligst prøv igjen.");
            alert.showAndWait();

        }

    }

    //Initialize metoden
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Kategori kat = new Kategori(1, "Tractor");
        kategorier.add(kat);
        Produkt prod = new Produkt(1, "John Deere", "Tractor", 1, "John Deere 5100R tractor");
        produkter.add(prod);

        //produktLagreTilFil();

        produkter.clear();
        kategorier.clear();
        lesKategoriFil();
        lesProduktFil();


        for(Kategori c: kategorier){
            kategorierListe.add(c.getKategori_Navn());
        }
        kategoriCbx.setItems(kategorierListe);


        data =  getInitialTableData();

        prodId.setCellValueFactory(new PropertyValueFactory<Produkt,String>("produkt_id"));
        prodNavn.setCellValueFactory(new PropertyValueFactory<Produkt,String>("Produkt_Navn"));
        prodKate.setCellValueFactory(new PropertyValueFactory<Produkt,String>("Category_Navn"));
        prodAnt.setCellValueFactory(new PropertyValueFactory<Produkt,Integer>("Antall"));
        prodBesk.setCellValueFactory(new PropertyValueFactory<Produkt,String>("Beskrivelse"));

        produktTabell.setItems(data);

        produktTabell.getSelectionModel()
                .selectedItemProperty().addListener((ChangeListener) (observableValue, oldValue, newValue) -> {
            //Sjekker om noe er valgt i tableview og deretter setter verdiene inn til textfields
            if (produktTabell.getSelectionModel().getSelectedItem() != null) {
                Produkt p = (Produkt) produktTabell.getSelectionModel().getSelectedItem();
                idTxt.setText(String.valueOf(p.getProdukt_id()));
                navnTxt.setText(p.getProdukt_Navn());
                antallTxt.setText(String.valueOf(p.getAntall()));
                kategoriCbx.setValue(p.getCategory_Navn());
                beskrivelseTxt.setText(p.getBeskrivelse());

            }
        });
    }

    //--METODER for lesing og skriving til fil--//

    //Lese fra produkt filen
    static void lesProduktFil(){

        try {
            File myObj = new File("produkter.txt");

            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (data.length() > 2 ) {
                        String[] arr = data.split(",");

                        Produkt produkt = new Produkt(Integer.parseInt(arr[0].trim()),
                                arr[1],
                                arr[2],
                                Integer.parseInt(arr[3].trim()),
                                arr[4]);

                        produkter.add(produkt);

                    }
                }

                myReader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Lesing fra produktfil, filen ikke funnet");
            e.printStackTrace();
        }

    }

    //Lese fra kategori filen
    static void lesKategoriFil(){

        try {
            File myObj = new File("kategorier.txt");

            if (myObj.exists()){
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (data.length() > 2) {
                        String[] arr = data.split(",");

                        kategorier.add(new Kategori(Integer.parseInt(arr[0].trim()), arr[1]));
                    }
                }
                myReader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("FEIL: Lesing fra kategorifil, filen ikke funnet");
            e.printStackTrace();
        }

    }

    //Lagre til produkt filen
    static void produktLagreTilFil(){

        try{
            File file = new File("produkter.txt");
            if(!file.exists()){

                file.createNewFile();
            }
            FileWriter fw=new FileWriter(file);

            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            for(Produkt p : produkter){
                pw.println(p.getProdukt_id()
                        +","+p.getProdukt_Navn()
                        +","+p.getCategory_Navn()
                        +","+p.getAntall()
                        +","+p.getBeskrivelse()+"\n");
            }

            pw.close();
            bw.close();
            fw.close();

        }catch(Exception e){
            System.out.println(e);}

    }



    private ObservableList getInitialTableData() {
        List list = new ArrayList();

        for (Produkt p: produkter) {
            list.add(p);
        }
        ObservableList data = FXCollections.observableList(list);

        return data;
    }
}
