package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Farmaco;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AssunzioneFarmacoController {

    @FXML private ComboBox<Farmaco> medicinali;
    @FXML private TextField dosaggioText;
    @FXML private TextField unitaText;
    @FXML private TextArea sintomiArea;
    @FXML private DatePicker datePicker;
    @FXML private TextField oraText;


    public void initialize(){

    }



}
