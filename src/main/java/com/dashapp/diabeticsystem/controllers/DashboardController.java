package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label ll;

    @FXML SidebarController sidebar ;


    public DashboardController(){
        this.sidebar = new SidebarController();
    }



    public void initData(Login user) {
        sidebar.setUserRole(user);
    }



}
