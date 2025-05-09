package com.dashapp.diabeticsystem.controllers.sidebars;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SidebarAdminController {

    @FXML private Button diabetologiButton;


    public void initialize(){
        this.diabetologiButton.setOnAction(e -> {
            System.out.println("Diabetologi button clicked");
        });
    }


}
