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
 *  StarterAppMain.java - An example of using the classes in the 
 *  javafx.scene.control package.
 *
 *  Developed 2011 by James L. Weaver jim.weaver [at] javafxpert.com
 *  as a JavaFX SDK 2.0 example for the Pro JavaFX book.
 */
package projavafx.starterapp.ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.HTMLEditorBuilder;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import projavafx.starterapp.model.Person;
import projavafx.starterapp.model.StarterAppModel;

public class StarterAppMain extends Application {

  // A reference to the model
  StarterAppModel model = new StarterAppModel();
  
  Stage stage;
  CheckBox checkBox;
  ContextMenu contextMenu;
  Label htmlLabel;
  Popup alertPopup;
  Tab webViewTab;
    
  public static void main(String[] args) {
    Application.launch(args);
  }
    
  @Override
  public void start(final Stage primaryStage) {
    stage = primaryStage;
    Scene scene = SceneBuilder.create()
      .width(800)
      .height(600)
      .stylesheets(StarterAppMain.class.getResource("starterApp.css")
                                 .toExternalForm())
      .root(
        BorderPaneBuilder.create()
          .top(
            VBoxBuilder.create()
              .children(
                createMenus(),
                createToolBar()
              )
              .build()
          )
          .center(createTabs())
          .build()
      )
      .build();
    
    stage.setScene(scene);
    stage.setTitle("Starter App");
    stage.show();
  }
  
  MenuBar createMenus() {
    MenuBar menuBar = MenuBarBuilder.create()
      .menus(
        MenuBuilder.create()
          .text("File")
          .items(
            MenuItemBuilder.create()
              .text("New...")
              .graphic((new ImageView(
                new Image(getClass().getResourceAsStream("images/paper.png"))))
              )
              .accelerator(KeyCombination.keyCombination("Ctrl+N"))
              .onAction(new EventHandler<ActionEvent>() {
                  @Override public void handle(ActionEvent e) {
                    System.out.println(e.getEventType() + 
                                       " occurred on MenuItem New");
                  }
                })
              .build(),
            MenuItemBuilder.create()
              .text("Save")
              .build()
          )
          .build(),
        MenuBuilder.create()
          .text("Edit")
          .items(
            MenuItemBuilder.create()
              .text("Cut")
              .build(),
            MenuItemBuilder.create()
              .text("Copy")
              .build(),
            MenuItemBuilder.create()
              .text("Paste")
              .build()
          )
          .build()
      )
      .build();
    
    return menuBar;
  }  
  
  ToolBar createToolBar() {
    final ToggleGroup alignToggleGroup = new ToggleGroup();
    ToolBar toolBar = ToolBarBuilder.create()
      .items(
        ButtonBuilder.create()
          .id("newButton")
          .graphic(new ImageView(
            new Image(getClass().getResourceAsStream("images/paper.png")))
          )
          .tooltip(new Tooltip("New Document... Ctrl+N"))
          .onAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
                System.out.println("New toolbar button clicked");
              }
            })
          .build(),
        ButtonBuilder.create()
          .id("editButton")
          .graphic(
            CircleBuilder.create()
              .fill(Color.GREEN)
              .radius(8)
              .build()
          )
          .build(),
        ButtonBuilder.create()
          .id("deleteButton")
          .graphic(
            CircleBuilder.create()
              .fill(Color.BLUE)
              .radius(8)
              .build()
          )
          .build(),
        new Separator(Orientation.VERTICAL),
        ToggleButtonBuilder.create()
          .id("boldButton")
          .graphic(
            CircleBuilder.create()
              .fill(Color.MAROON)
              .radius(8)
              .build()
          )
          .onAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
                ToggleButton tb = ((ToggleButton)e.getTarget());
                System.out.print(e.getEventType() + " occurred on ToggleButton " 
                                 + tb.getId());
                System.out.print(", and selectedProperty is: ");
                System.out.println(tb.selectedProperty().getValue());
              }
            })
          .build(),
        ToggleButtonBuilder.create()
          .id("italicButton")
          .graphic(
            CircleBuilder.create()
              .fill(Color.YELLOW)
              .radius(8)
              .build()
          )
          .onAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
                ToggleButton tb = ((ToggleButton)e.getTarget());
                System.out.print(e.getEventType() + " occurred on ToggleButton " 
                                 + tb.getId());
                System.out.print(", and selectedProperty is: ");
                System.out.println(tb.selectedProperty().getValue());
              }
            })
          .build(),
        new Separator(Orientation.VERTICAL),
        ToggleButtonBuilder.create()
          .id("leftAlignButton")
          .toggleGroup(alignToggleGroup)
          .graphic(
            CircleBuilder.create()
              .fill(Color.PURPLE)
              .radius(8)
              .build()
          )
          .build(),
        ToggleButtonBuilder.create()
          .id("centerAlignButton")
          .toggleGroup(alignToggleGroup)
          .graphic(
            CircleBuilder.create()
              .fill(Color.ORANGE)
              .radius(8)
              .build()
          )
          .build(),
        ToggleButtonBuilder.create()
          .id("rightAlignButton")
          .toggleGroup(alignToggleGroup)
          .graphic(
            CircleBuilder.create()
              .fill(Color.CYAN)
              .radius(8)
              .build()
          )
          .build()
      )
      .build();
    
    alignToggleGroup.selectToggle(alignToggleGroup.getToggles().get(0));
    alignToggleGroup.selectedToggleProperty().addListener(new ChangeListener() {
      public void changed(ObservableValue ov, Object oldValue, Object newValue) {
        ToggleButton tb = ((ToggleButton)alignToggleGroup.getSelectedToggle());
        if (tb != null) {
            System.out.println(tb.getId() + " selected");
        }
      }
    });
    
    return toolBar;
  }  

  TabPane createTabs() {
    final WebView webView;
    TabPane tabPane = TabPaneBuilder.create()
      .tabs(
        TabBuilder.create()
          .text("TableView")
          .content(createTableDemoNode())
          .closable(false)
          .build(),
        TabBuilder.create()
          .text("Accordion/TitledPane")
          .content(createAccordionTitledDemoNode())
          .closable(false)
          .build(),
        TabBuilder.create()
          .text("SplitPane/TreeView/ListView")
          .content(createSplitTreeListDemoNode())
          .closable(false)
          .build(),
        TabBuilder.create()
          .text("ScrollPane/Miscellaneous")
          .content(createScrollMiscDemoNode())
          .closable(false)
          .build(),
        TabBuilder.create()
          .text("HTMLEditor")
          .content(createHtmlEditorDemoNode())
          .closable(false)
          .build(),
        webViewTab = TabBuilder.create()
          .text("WebView")
          .content(
            webView = WebViewBuilder.create()
              .build()
          )
          .closable(false)
          .onSelectionChanged(new EventHandler<Event>() {
            public void handle(Event evt) {
              String randomWebSite = model.getRandomWebSite();
              if (webViewTab.isSelected()) {
                webView.getEngine().load(randomWebSite);
                System.out.println("WebView tab is selected, loading: " 
                                   + randomWebSite);
              }
            }
          })
          .build()
      )
      .build();
    
    return tabPane;
  }  
    
  Node createTableDemoNode() {
    TableView table = TableViewBuilder.create()
      .columns(
        TableColumnBuilder.create()
          .text("First Name")
          .cellValueFactory(new PropertyValueFactory("firstName"))
          .prefWidth(180)
          .build(),
        TableColumnBuilder.create()
          .text("Last Name")
          .cellValueFactory(new PropertyValueFactory("lastName"))
          .prefWidth(180)
          .build(),
        TableColumnBuilder.create()
          .text("Phone Number")
          .cellValueFactory(new PropertyValueFactory("phone"))
          .prefWidth(150)
          .build()
      )
      .items(model.getTeamMembers())
      .build();
    
    table.getSelectionModel().selectedItemProperty()
                                .addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue,
                          Object newValue) {
        Person selectedPerson = (Person)newValue;
        System.out.println(selectedPerson + " chosen in TableView");
      }
    });
    return table;
  }
  
  Node createAccordionTitledDemoNode() {
    TitledPane firstPane;
    Accordion accordion = AccordionBuilder.create()
      .panes(
        firstPane = TitledPaneBuilder.create()
          .text("TitledPane A")
          .content(new TextArea("TitledPane A content"))
          .build(),
        TitledPaneBuilder.create()
          .text("TitledPane B")
          .content(new TextArea("TitledPane B content"))
          .build(),
        TitledPaneBuilder.create()
          .text("TitledPane C")
          .content(new TextArea("TitledPane C content"))
          .build()
      )
      .build();
    
    accordion.setExpandedPane(firstPane);
    return accordion;
  }
  
  Node createSplitTreeListDemoNode() {
    TreeView treeView = TreeViewBuilder.create()
      .minWidth(150)
      .showRoot(false)
      .editable(false)
      .root(
        TreeItemBuilder.create()
          .value("Root")
          .children(
            TreeItemBuilder.create()
              .value("Animal")
              .children(
                TreeItemBuilder.create()
                  .value("Lion")
                  .build(),
                TreeItemBuilder.create()
                  .value("Tiger")
                  .build(),
                TreeItemBuilder.create()
                  .value("Bear")
                  .build()
              )
              .build(),
            TreeItemBuilder.create()
              .value("Mineral")
              .children(
                TreeItemBuilder.create()
                  .value("Copper")
                  .build(),
                TreeItemBuilder.create()
                  .value("Diamond")
                  .build(),
                TreeItemBuilder.create()
                  .value("Quartz")
                  .build()
              )
              .build(),
            TreeItemBuilder.create()
              .value("Vegetable")
              .children(
                TreeItemBuilder.create()
                  .value("Arugula")
                  .build(),
                TreeItemBuilder.create()
                  .value("Broccoli")
                  .build(),
                TreeItemBuilder.create()
                  .value("Cabbage")
                  .build()
              )
              .build()
           )
          .build()
       )
      .build();
    
    ListView listView = ListViewBuilder.create()
      .items(model.listViewItems)
      .build();

    SplitPane splitPane = SplitPaneBuilder.create()
      .items(
        treeView,
        listView
      )
      .build();

    treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    treeView.getSelectionModel().selectedItemProperty()
                                .addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue,
                          Object newValue) {
        TreeItem treeItem = (TreeItem)newValue;
        if (newValue != null && treeItem.isLeaf()) {
          model.listViewItems.clear();
          for (int i = 1; i <= 10000; i++) {
            model.listViewItems.add(treeItem.getValue() + " " + i);
          }
        }
      }
    });
    
    return splitPane;
  }
  
  Node createScrollMiscDemoNode() {
    final ToggleGroup radioToggleGroup = new ToggleGroup();
    ChoiceBox choiceBox;
    final TextField textField;
    final PasswordField passwordField;
    final TextArea textArea;
    Slider slider;
    ProgressIndicator progressIndicator;
    ProgressBar progressBar;
    ScrollBar scrollBar;
    VBox variousControls = VBoxBuilder.create()
      .padding(new Insets(10, 10, 10, 10))
      .spacing(20)
      .children(
        ButtonBuilder.create()
          .text("Button")
          .onAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
                System.out.println(e.getEventType() + " occurred on Button");
              }
            })
          .build(),
        checkBox = CheckBoxBuilder.create()
          .text("CheckBox")
          .onAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
                System.out.print(e.getEventType() + " occurred on CheckBox");
                System.out.print(", and selectedProperty is: ");
                System.out.println(checkBox.selectedProperty().getValue());
              }
            })
          .build(),
        HBoxBuilder.create()
          .spacing(10)
          .children(
            RadioButtonBuilder.create()
              .text("RadioButton1")
              .toggleGroup(radioToggleGroup)
              .build(),
            RadioButtonBuilder.create()
              .text("RadioButton2")
              .toggleGroup(radioToggleGroup)
              .build()
          )
          .build(),
        HyperlinkBuilder.create()
          .text("Hyperlink")
          .onAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
                System.out.println(e.getEventType() + " occurred on Hyperlink");
              }
            })
          .build(),
        choiceBox = ChoiceBoxBuilder.create()
          .items(model.choiceBoxItems)
          .build(),
        MenuButtonBuilder.create()
          .text("MenuButton")
          .items(
            MenuItemBuilder.create()
              .text("MenuItem A")
              .onAction(new EventHandler<ActionEvent>() {
                  @Override public void handle(ActionEvent e) {
                    System.out.println(e.getEventType() + 
                                       " occurred on Menu Item A");
                  }
                })
              .build(),
            MenuItemBuilder.create()
              .text("MenuItem B")
              .build()
           )
          .build(),
        SplitMenuButtonBuilder.create()
          .text("SplitMenuButton")
          .onAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
              System.out.println(e.getEventType() + 
                                 " occurred on SplitMenuButton");
            }
          })
          .items(
            MenuItemBuilder.create()
              .text("MenuItem A")
              .onAction(new EventHandler<ActionEvent>() {
                  @Override public void handle(ActionEvent e) {
                    System.out.println(e.getEventType() + 
                                       " occurred on Menu Item A");
                  }
                })
              .build(),
            MenuItemBuilder.create()
              .text("MenuItem B")
              .build()
           )
          .build(),
        textField = TextFieldBuilder.create()
          .promptText("Enter user name")
          .prefColumnCount(16)
          .build(),
        passwordField = PasswordFieldBuilder.create()
          .promptText("Enter password")
          .prefColumnCount(16)
          .build(),
        HBoxBuilder.create()
          .spacing(10)
          .children(
            new Label("TextArea:"),
            textArea = TextAreaBuilder.create()
              .prefColumnCount(12)
              .prefRowCount(4)
              .build()
          )
          .build(),
        progressIndicator = ProgressIndicatorBuilder.create()
          .prefWidth(200)
          .build(),
        slider = SliderBuilder.create()
          .prefWidth(200)
          .min(-1)
          .max(model.maxRpm)
          .build(),
        progressBar = ProgressBarBuilder.create()
          .prefWidth(200)
          .build(),
        scrollBar = ScrollBarBuilder.create()
          .prefWidth(200)
          .min(-1)
          .max(model.maxKph)
          .build()
      )
      .build();
    
    radioToggleGroup.selectToggle(radioToggleGroup.getToggles().get(0));
    radioToggleGroup.selectedToggleProperty().addListener(new ChangeListener() {
      public void changed(ObservableValue ov, Object oldValue, Object newValue) {
        RadioButton rb = ((RadioButton)radioToggleGroup.getSelectedToggle());
        if (rb != null) {
          System.out.println(rb.getText() + " selected");
        }
      }
    });
    
    textField.textProperty().addListener(new ChangeListener() {
      public void changed(ObservableValue ov, Object oldValue, Object newValue) {
        System.out.println("TextField text is: " + textField.getText());
      }
    });
    
    passwordField.focusedProperty().addListener(new ChangeListener() {
      public void changed(ObservableValue ov, Object oldValue, Object newValue) {
        if (!passwordField.isFocused()) {
          System.out.println("PasswordField text is: " 
                               + passwordField.getText());
        }
      }
    });
    
    textArea.focusedProperty().addListener(new ChangeListener() {
      public void changed(ObservableValue ov, Object oldValue, Object newValue) {
        if (!textArea.isFocused()) {
          System.out.println("TextArea text is: " + textArea.getText());
        }
      }
    });
    
    slider.valueProperty().bindBidirectional(model.rpm);
    progressIndicator.progressProperty().bind(model.rpm.divide(model.maxRpm));
    
    scrollBar.valueProperty().bindBidirectional(model.kph);
    progressBar.progressProperty().bind(model.kph.divide(model.maxKph));
    
    choiceBox.getSelectionModel().selectFirst();
    choiceBox.getSelectionModel().selectedItemProperty()
                                .addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue,
                          Object newValue) {
        System.out.println(newValue + " chosen in ChoiceBox");
      }
    });
    
    ScrollPane scrollPane = ScrollPaneBuilder.create()
      .content(variousControls)
      .hbarPolicy(ScrollPane.ScrollBarPolicy.NEVER)
      .vbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED)
      .onMousePressed(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
          if (me.getButton() == MouseButton.SECONDARY) {
            contextMenu.show(stage, me.getScreenX(), me.getScreenY());
          }
        }
      })
      .build();

    contextMenu = ContextMenuBuilder.create()
      .items(
        MenuItemBuilder.create()
          .text("MenuItem A")
          .onAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
                System.out.println(e.getEventType() + 
                                   " occurred on Menu Item A");
              }
            })
          .build(),
        MenuItemBuilder.create()
          .text("MenuItem B")
          .build()
       )
      .build();

    return scrollPane;
  }
  
  Node createHtmlEditorDemoNode() {
   final BorderPane htmlEditorDemo;
   final HTMLEditor htmlEditor;
   Button viewHtmlButton;
    
    htmlEditorDemo = BorderPaneBuilder.create()
      .center(
        htmlEditor = HTMLEditorBuilder.create()
          .htmlText("<p>Replace this text</p>")
          .build()
      )
      .bottom(
        viewHtmlButton = ButtonBuilder.create()
          .text("View HTML")
          .onAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
              Popup alertPopup = createAlertPopup(htmlEditor.getHtmlText());
              alertPopup.show(stage, 
                (stage.getWidth() - alertPopup.getWidth()) / 2 + stage.getX(), 
                (stage.getHeight() - alertPopup.getHeight()) / 2 + stage.getY());
            }
          })
          .build()
      )      
      .build();

    BorderPane.setAlignment(viewHtmlButton, Pos.CENTER);
    BorderPane.setMargin(viewHtmlButton, new Insets(10, 0, 10, 0));  
    return htmlEditorDemo;
  }
  
  Popup createAlertPopup(String text) {
    Button okButton;
    alertPopup = PopupBuilder.create()
      .content(
        StackPaneBuilder.create()
          .children(
            RectangleBuilder.create()
              .width(300)
              .height(200)
              .arcWidth(20)
              .arcHeight(20)
              .fill(Color.LIGHTBLUE)
              .stroke(Color.GRAY)
              .strokeWidth(2)
              .build(),
              BorderPaneBuilder.create()
                .center(
                  htmlLabel = LabelBuilder.create()
                    .text(text)
                    .wrapText(true)
                    .maxWidth(280)
                    .maxHeight(140)
                    .build()
                )
                .bottom(
                  okButton = ButtonBuilder.create()
                    .text("OK")
                    .onAction(new EventHandler<ActionEvent>() {
                      @Override public void handle(ActionEvent e) {
                        alertPopup.hide();
                      }
                    })
                    .build() 
                )
                .build()
          )
          .build()
      )
      .build();
        
    BorderPane.setAlignment(okButton, Pos.CENTER);
    BorderPane.setMargin(okButton, new Insets(10, 0, 10, 0));
    return alertPopup;
  }  
}
