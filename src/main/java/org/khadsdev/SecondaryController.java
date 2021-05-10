package org.khadsdev;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

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

public class SecondaryController implements Initializable {

    static List<Kategori> kategorier = new ArrayList<Kategori>();

    @FXML
    private TextField katIdTxt;
    @FXML
    private TextField katNavnTxt;
    @FXML
    private ObservableList<String> kategorierListe = FXCollections.observableArrayList();

    @FXML
    private ListView<String> list;

    @FXML
    private TableView kategoriTabel;

    @FXML
    private TableColumn katId = new TableColumn();

    @FXML
    private TableColumn katNavn = new TableColumn();

    private ObservableList<Produkt> data;


    //----BUTTONS----//

    //Tøm alle feltene
    @FXML
    private void handleTømFelt() {
        katIdTxt.clear();
        katNavnTxt.clear();
        kategoriTabel.getSelectionModel().clearSelection();
    }

    //Bytt tilbake til primary scenen.
    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        Parent kategoriParent = FXMLLoader.load(getClass().getResource("primary.fxml"));
        Scene kategoriScene = new Scene(kategoriParent);

        //Denne linjen henter Stage infoen.
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(kategoriScene);
        window.show();
    }

    //Slett en kategori
    @FXML
    private void handleSlett() {

        int ID = Integer.parseInt(katIdTxt.getText());
        boolean deleted = false;

        for (Kategori p : kategorier) {

            if (p.getKategori_Id() == ID) {

                kategorier.remove(p);
                deleted = true;
                break;
            }
        }

        if (deleted == true) {

            System.out.println("Kategori Slettet");

            data = getInitialTableData();
            kategoriTabel.setItems(data);

            katIdTxt.setText("");
            katNavnTxt.setText("");

            kategorierLagreTilFil();

        } else {
            System.out.println("Kategori ikke slettet noe gikk galt");
        }
    }

    //Endre en kategori
    @FXML
    private void handleEndre() {

        String KategoriID = katIdTxt.getText();
        String Navn = katNavnTxt.getText();

        Kategori kategori = new Kategori(Integer.parseInt(KategoriID.trim()), Navn);

        boolean updated = false;

        for(Kategori c : kategorier){

            if(c.getKategori_Id() == kategori.getKategori_Id()){

                kategorier.remove(c);
                updated = kategorier.add(kategori);
                updated = true;
                break;
            }
        }

        if(updated == true) {

            System.out.println("Kategori endret");

            data = getInitialTableData();
            kategoriTabel.setItems(data);

            katIdTxt.setText("");
            katNavnTxt.setText("");

            kategorierLagreTilFil();

        } else {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Feil Melding");
            alert.setHeaderText("Kategori ID kan ikke endres");
            alert.setContentText("Endre bare navn og ikke Kategori ID.");
            alert.showAndWait();
            System.out.println("Kategori ikke endret, noe gikk galt");
        }
    }

    //Legg til ny kategori
    @FXML
    private void handleLeggTilKat(ActionEvent event) {

        try {
            String KategoriID = katIdTxt.getText();
            String Navn = katNavnTxt.getText();

            Kategori kategori = new Kategori(Integer.parseInt(KategoriID.trim()), Navn);

            boolean added = kategorier.add(kategori);

            if(added == true){
                System.out.println("Kategori lagt til");

                data = getInitialTableData();
                kategoriTabel.setItems(data);

                katIdTxt.setText("");
                katNavnTxt.setText("");

                kategorierLagreTilFil();

            }else{
                System.out.println("Kategori ikke lagt til, noe gikk galt");
            }

        } catch (NumberFormatException e) {

            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Feil Melding");
            alert.setHeaderText("Kategori ID må være en number");
            alert.setContentText("Du skrev inn ugyldig data, vennligst prøv igjen.");
            alert.showAndWait();

        }

    }


    public void initialize(URL url, ResourceBundle rb) {
        kategorier.add(new Kategori(1, "Tractor"));

        //kategorierLagreTilFil();
        kategorier.clear();

        lesKategoriFil();

        data =  getInitialTableData();

        katId.setCellValueFactory(new PropertyValueFactory<Produkt,String>("kategori_Id"));
        katNavn.setCellValueFactory(new PropertyValueFactory<Produkt,String>("kategori_Navn"));

        kategoriTabel.setItems(data);

        kategoriTabel.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Sjekker om noe er valgt og setter verdiene i label
            if(kategoriTabel.getSelectionModel().getSelectedItem() != null)
            {
                Kategori c = (Kategori) kategoriTabel.getSelectionModel().getSelectedItem();
                katIdTxt.setText(String.valueOf(c.getKategori_Id()));
                katNavnTxt.setText(c.getKategori_Navn());

            }
        });
    }

    //--METODER for lesing og skriving til fil--//

    //Metode for å lese fra kategori filen
    static void lesKategoriFil(){
        try {
            File myObj = new File("kategorier.txt");

            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);

                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    if (data.length() > 2 ) {
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


    //Metode for å lagre til kategori filen
    static void kategorierLagreTilFil(){
        try{
            File file = new File("kategorier.txt");
            if (!file.exists()) {

                file.createNewFile();
            }
            FileWriter fw=new FileWriter(file);

            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            for (Kategori c : kategorier) {
                pw.println(c.getKategori_Id()+","+c.getKategori_Navn()+"\n");
            }
            pw.close();
            bw.close();
            fw.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    //getInitialData metode.
    private ObservableList getInitialTableData() {

        List list = new ArrayList();

        for
        (Kategori c: kategorier){

            list.add(c);

        }
        ObservableList data = FXCollections.observableList(list);

        return data;
    }
}