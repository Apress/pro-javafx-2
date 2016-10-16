/*
 * Copyright (c) 2011, Pro JavaFX Authors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of JFXtras nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * OnTheSceneMain.java - A JavaFX Script example program that demonstrates
 * how to use the Scene class in JavaFX, and displays many of the properties'
 * values as the Scene is manipulated by the user.
 *
 *  Developed 2011 by James L. Weaver jim.weaver [at] javafxpert.com
 *  as a JavaFX SDK 2.0 example for the Pro JavaFX book.
 */
package projavafx.onthescene.ui;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceBoxBuilder;
import javafx.scene.control.HyperlinkBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioButtonBuilder;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;

public class OnTheSceneMain extends Application {
  DoubleProperty fillVals = new SimpleDoubleProperty(255.0);

  Scene sceneRef;
  
  ObservableList cursors = FXCollections.observableArrayList(
      Cursor.DEFAULT,
      Cursor.CROSSHAIR,
      Cursor.WAIT,
      Cursor.TEXT,
      Cursor.HAND,
      Cursor.MOVE,
      Cursor.N_RESIZE,
      Cursor.NE_RESIZE,
      Cursor.E_RESIZE,
      Cursor.SE_RESIZE,
      Cursor.S_RESIZE,
      Cursor.SW_RESIZE,
      Cursor.W_RESIZE,
      Cursor.NW_RESIZE,
      Cursor.NONE
    );  
  
  public static void main(String[] args) {
    Application.launch(args);
  }
  
  @Override
  public void start(Stage stage) {
    Slider sliderRef;
    ChoiceBox choiceBoxRef;
    Text textSceneX;
    Text textSceneY;
    Text textSceneW;
    Text textSceneH;
    Label labelStageX;
    Label labelStageY;
    Label labelStageW;
    Label labelStageH;
    
    final ToggleGroup toggleGrp = new ToggleGroup();
  
    FlowPane sceneRoot = FlowPaneBuilder.create()
      .layoutX(20)
      .layoutY(40)
      .padding(new Insets(0, 20, 40, 0))
      .orientation(Orientation.VERTICAL)
      .vgap(10)
      .hgap(20)
      .columnHalignment(HPos.LEFT)
      .children(
        HBoxBuilder.create()
          .spacing(10)
          .children(
            sliderRef = SliderBuilder.create()
              .min(0)
              .max(255)
              .value(255)
              .orientation(Orientation.VERTICAL)
              .build(),
            choiceBoxRef = ChoiceBoxBuilder.create()
              .items(cursors)
              .build()
          )
          .build(),
        textSceneX = TextBuilder.create()
          .styleClass("emphasized-text")
          .build(),
        textSceneY = TextBuilder.create()
          .styleClass("emphasized-text")
          .build(),
        textSceneW = TextBuilder.create()
          .styleClass("emphasized-text")
          .build(),
        textSceneH = TextBuilder.create()
          .styleClass("emphasized-text")
          .id("sceneHeightText")
          .build(),
        HyperlinkBuilder.create()
          .text("lookup()")
          .onAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override public void handle(javafx.event.ActionEvent e) {
              System.out.println("sceneRef:" + sceneRef);
              Text textRef = (Text)sceneRef.lookup("#sceneHeightText");
              System.out.println(textRef.getText());
            }
          })
          .build(),
        RadioButtonBuilder.create()
          .text("onTheScene.css")
          .toggleGroup(toggleGrp)
          .selected(true)
          .build(),
        RadioButtonBuilder.create()
          .text("changeOfScene.css")
          .toggleGroup(toggleGrp)
          .build(),
        labelStageX = LabelBuilder.create()
          .id("stageX")
          .build(),
        labelStageY = LabelBuilder.create()
          .id("stageY")
          .build(),
        labelStageW = new Label(),
        labelStageH = new Label()
      )
      .build();

    sceneRef = SceneBuilder.create()
      .width(600)
      .height(250)
      .root(sceneRoot)
      .build();
    
    sceneRef.getStylesheets().addAll(OnTheSceneMain.class
            .getResource("onTheScene.css").toExternalForm());
    stage.setScene(sceneRef);
    
    choiceBoxRef.getSelectionModel().selectFirst();
    
    // Setup various property binding
    textSceneX.textProperty().bind(new SimpleStringProperty("Scene x: ")
            .concat(sceneRef.xProperty().asString()));
    textSceneY.textProperty().bind(new SimpleStringProperty("Scene y: ")
            .concat(sceneRef.yProperty().asString()));
    textSceneW.textProperty().bind(new SimpleStringProperty("Scene width: ")
            .concat(sceneRef.widthProperty().asString()));
    textSceneH.textProperty().bind(new SimpleStringProperty("Scene height: ")
            .concat(sceneRef.heightProperty().asString()));
    labelStageX.textProperty().bind(new SimpleStringProperty("Stage x: ")
            .concat(sceneRef.getWindow().xProperty().asString()));
    labelStageY.textProperty().bind(new SimpleStringProperty("Stage y: ")
            .concat(sceneRef.getWindow().yProperty().asString()));
    labelStageW.textProperty().bind(new SimpleStringProperty("Stage width: ")
            .concat(sceneRef.getWindow().widthProperty().asString()));
    labelStageH.textProperty().bind(new SimpleStringProperty("Stage height: ")
            .concat(sceneRef.getWindow().heightProperty().asString()));
    sceneRef.cursorProperty().bind(choiceBoxRef.getSelectionModel()
            .selectedItemProperty());
    fillVals.bind(sliderRef.valueProperty());
   
    // When fillVals changes, use that value as the RGB to fill the scene
    fillVals.addListener(new ChangeListener() {
      public void changed(ObservableValue ov, Object oldValue, Object newValue) {
        Double fillValue = fillVals.getValue() / 256.0;
        sceneRef.setFill(new Color(fillValue, fillValue, fillValue, 1.0));
      }
    });
    
    // When the selected radio button changes, set the appropriate stylesheet
    toggleGrp.selectedToggleProperty().addListener(new ChangeListener() {
      public void changed(ObservableValue ov, Object oldValue, Object newValue) {
        String radioButtonText = ((RadioButton)toggleGrp.getSelectedToggle())
                .getText();
        sceneRef.getStylesheets().addAll(OnTheSceneMain.class
                .getResource(radioButtonText).toExternalForm());
      }
    });
    
    stage.setTitle("On the Scene");
    stage.show();
    
    // Define an unmanaged node that will display Text 
    Text addedTextRef = TextBuilder.create()
      .layoutX(0)
      .layoutY(-30)
      .textOrigin(VPos.TOP)
      .fill(Color.BLUE)
      .font(Font.font("Sans Serif", FontWeight.BOLD, 16))
      .managed(false)
      .build();
    
    // Bind the text of the added Text node to the fill property of the Scene
    addedTextRef.textProperty().bind(new SimpleStringProperty("Scene fill: ").
            concat(sceneRef.fillProperty()));
    
    // Add to the Text node to the FlowPane
    ((FlowPane)sceneRef.getRoot()).getChildren().add(addedTextRef);
  }
}
