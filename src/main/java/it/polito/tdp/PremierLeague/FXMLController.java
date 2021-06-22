/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenze;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<String> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	txtResult.clear();
    	
    	List<Adiacenze> result = model.getConnessioniMax();
    	for (Adiacenze a: result) {
    		txtResult.appendText(a.descriviti());
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String mese = cmbMese.getValue();
    	String minuti = txtMinuti.getText();
    	Integer min;
    	if (mese==null || minuti ==null) {
    		txtResult.appendText("Inserisci i valori di input!");
    		return;
    	}
    	
    	try {
    		min= Integer.parseInt(minuti);
    		
    	}catch (NumberFormatException e) {
    		txtResult.setText("Inserisci un valore numerico");
    		return;
    	}
    	int numMese = this.calcolaMese(mese);
    	model.creaGrafo(numMese, min);
    	
    	cmbM1.getItems().addAll(model.getVertex());
    	cmbM2.getItems().addAll(model.getVertex());
    	
    	txtResult.appendText("Grafo creato\n #VERTICI: "+ model.getNumVertici()+ " #ARCHI: "+ model.getNArchi()+"\n");
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	txtResult.clear();
    	Match m1= cmbM1.getValue();
    	Match m2= cmbM2.getValue();
    	if (m1==null || m2==null) {
    		txtResult.appendText("SELEZIONA I DUE MATCH!!");
    	}
    	List<Match> percorso = model.getPercorso(m1, m2);
    	for (Match m: percorso) {
    		txtResult.appendText(m+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
    	cmbMese.getItems().addAll(mesi);
    }
    
    public int calcolaMese (String mese) {
    	if(mese.equals("Gennaio"))
    		return 1;
    	if(mese.equals("Febbraio"))
    		return 2;
    	if(mese.equals("Marzo"))
    		return 3;
    	if(mese.equals("Aprile"))
    		return 4;
    	if(mese.equals("Maggio"))
    		return 5;
    	if(mese.equals("Giugno"))
    		return 6;
    	if(mese.equals("Luglio"))
    		return 7;
    	if(mese.equals("Agosto"))
    		return 8;
    	if(mese.equals("Settembre"))
    		return 9;
    	if(mese.equals("Ottobre"))
    		return 10;
    	if(mese.equals("Novembre"))
    		return 11;
    	if(mese.equals("Dicembre"))
    		return 12;
    	return -1;
    }
    
    
}
