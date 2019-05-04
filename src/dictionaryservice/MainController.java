/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryservice;

import com.aonaware.services.webservices.ArrayOfDefinition;
import com.aonaware.services.webservices.ArrayOfDictionary;
import com.aonaware.services.webservices.Definition;
import com.aonaware.services.webservices.DictService;
import com.aonaware.services.webservices.DictServiceSoap;
import com.aonaware.services.webservices.Dictionary;
import com.aonaware.services.webservices.DictionaryWord;
import com.aonaware.services.webservices.WordDefinition;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 *
 * @author slinger
 */
public class MainController implements Initializable {

    private Label label;
    @FXML
    private JFXTextArea textArea;
    @FXML
    private JFXTextField word;

    DictService object = new DictService();
    DictServiceSoap dictServiceSoap = object.getDictServiceSoap();
    @FXML
    private JFXComboBox<String> dictionaries;
    private Map<String,Integer> dictionaryIDs;
    private int count = 0;
    @FXML
    private TableView<?> recentSearches;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dictionaryIDs = new HashMap<>();
       
        //show dictionaries in combobox
        ArrayOfDictionary dictionaryList = dictServiceSoap.dictionaryList();
        for (int i = 0; i < dictionaryList.getDictionary().size(); i++) {
            String dictionaryName = dictServiceSoap.dictionaryList().getDictionary().get(i).getName();
            //String dictionaryId = dictServiceSoap.dictionaryList().getDictionary().get(i).getId();
            dictionaries.getItems().add(dictionaryName);
            dictionaryIDs.put(dictionaryName, count);
            count++;
        }
    }

    @FXML
    private void lookUpWord(ActionEvent event) {
        
        String dictionaryChosen = dictionaries.getValue();
        String lookup = word.getText();
       
        //WordDefinition defination = dictServiceSoap.defineInDict(dictionaryIDs.get(lookup), lookup);
        try {
            textArea.setText(dictServiceSoap.define(lookup).getDefinitions().getDefinition().get(dictionaryIDs.get(dictionaryChosen)).getWordDefinition());
        } catch (Exception e) {
            textArea.setText(dictionaryChosen + " does not contain a definition for " + lookup);
        }
        
        
        
        
    }

}
