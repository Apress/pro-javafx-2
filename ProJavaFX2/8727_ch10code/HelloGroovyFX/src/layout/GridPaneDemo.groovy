package layout

import groovyx.javafx.GroovyFX
import groovyx.javafx.SceneGraphBuilder
import javafx.scene.layout.GridPane
import javafx.scene.text.Font

GroovyFX.start {
    def sg = new SceneGraphBuilder()

    sg.stage(title: "GridPane Demo", width: 400, height: 500, visible: true) {
        scene {
            stackPane {
                imageView {
                    image("puppy.jpg", width: 1100, height: 1100, preserveRatio: true)
                    effect colorAdjust(brightness: 0.6, input: gaussianBlur())
                }
                gridPane(hgap: 10, vgap: 10, padding: 20) {
                    columnConstraints(minWidth: 60, halignment: "right")
                    columnConstraints(prefWidth: 300, hgrow: "always")

                    label("Dog Adoption Form", font: new Font(24), margin: [0, 0, 10, 0],
                            halignment: "center", columnSpan: GridPane.REMAINING)

                    label("Size: ", row: 2)
                    textField(promptText: "approximate size in pounds", row: 2, column: 1)

                    label("Breed:", row: 3)
                    textField(promptText: "pet breed", row: 3, column: 1)

                    label("Sex:", row: 4)
                    choiceBox(items: ['Male', 'Female', 'Either'], row: 4, column: 1)

                    label("Additional Info:", wrapText: true, textAlignment: "right",
                            row: 5, valignment: "baseline")
                    textArea(prefRowCount: 8, wrapText: true, row: 5, column: 1, vgrow: 'always')

                    button("Submit", row: 6, column: 1, halignment: "right")
                }
            }
        }
    }
}
