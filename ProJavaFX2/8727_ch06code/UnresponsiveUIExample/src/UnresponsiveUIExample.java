import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;

public class UnresponsiveUIExample extends Application {
    private Model model;
    private View view;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public UnresponsiveUIExample() {
        model = new Model();
    }

    @Override
    public void start(Stage stage) throws Exception {
        view = new View(model);
        hookupEvents();
        stage.setTitle("Unresponsive UI Example");
        stage.setScene(view.scene);
        stage.show();
    }

    private void hookupEvents() {
        view.changeFillButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Paint fillPaint = model.getFillPaint();
                if (fillPaint.equals(Color.LIGHTGRAY)) {
                    model.setFillPaint(Color.GRAY);
                } else {
                    model.setFillPaint(Color.LIGHTGRAY);
                }
                // Bad code, this will cause the UI to be unresponsive
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    //  TODO properly handle interruption
                }
            }
        });

        view.changeStrokeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Paint strokePaint = model.getStrokePaint();
                if (strokePaint.equals(Color.DARKGRAY)) {
                    model.setStrokePaint(Color.BLACK);
                } else {
                    model.setStrokePaint(Color.DARKGRAY);
                }
            }
        });
    }

    private static class Model {
        private ObjectProperty<Paint> fillPaint = new SimpleObjectProperty<>();
        private ObjectProperty<Paint> strokePaint = new SimpleObjectProperty<>();

        private Model() {
            fillPaint.set(Color.LIGHTGRAY);
            strokePaint.set(Color.DARKGRAY);
        }

        final public Paint getFillPaint() {
            return fillPaint.get();
        }

        final public void setFillPaint(Paint value) {
            this.fillPaint.set(value);
        }

        final public Paint getStrokePaint() {
            return strokePaint.get();
        }

        final public void setStrokePaint(Paint value) {
            this.strokePaint.set(value);
        }

        final public ObjectProperty<Paint> fillPaintProperty() {
            return fillPaint;
        }

        final public ObjectProperty<Paint> strokePaintProperty() {
            return strokePaint;
        }
    }

    private static class View {
        public Rectangle rectangle;
        public Button changeFillButton;
        public Button changeStrokeButton;
        public HBox buttonHBox;
        public Scene scene;

        private View(Model model) {
            rectangle = RectangleBuilder.create()
                .width(200)
                .height(200)
                .strokeWidth(10)
                .build();
            rectangle.fillProperty().bind(model.fillPaintProperty());
            rectangle.strokeProperty().bind(model.strokePaintProperty());

            changeFillButton = new Button("Change Fill");
            changeStrokeButton = new Button("Change Stroke");

            buttonHBox = HBoxBuilder.create()
                .padding(new Insets(10, 10, 10, 10))
                .spacing(10)
                .alignment(Pos.CENTER)
                .children(changeFillButton, changeStrokeButton)
                .build();

            scene = SceneBuilder.create()
                .root(BorderPaneBuilder.create()
                    .padding(new Insets(10, 10, 10, 10))
                    .center(rectangle)
                    .bottom(buttonHBox)
                    .build())
                .build();
        }
    }
}
