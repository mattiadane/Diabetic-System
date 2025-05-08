package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label ll;




    public void initData(Login user) {
        ll.setText("Benvenuto "  + user);
    }



}
