package com.projavafx.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class AdoptionFormController implements Initializable {
    @FXML
    private GridPane grid;
    
    @FXML
    private void handleSubmit(ActionEvent event) {
        grid.setGridLinesVisible(!grid.isGridLinesVisible());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // no init action required
    }
}
