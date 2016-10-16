package table

import groovyx.javafx.GroovyFX
import groovyx.javafx.SceneGraphBuilder
import binding.Person
import java.text.SimpleDateFormat

def dateFormat = new SimpleDateFormat("MM/dd/yyyy")

def persons = [
    new Person(name: "Ada Lovelace", age: 36, gender: "Female", dob: dateFormat.parse("10/10/1815")),
    new Person(name: "Henrietta Swan Leavitt", age: 53, gender: "Female", dob: dateFormat.parse("7/4/1868")),
    new Person(name: "Grete Hermann", age: 83, gender: "Female", dob: dateFormat.parse("3/2/1901"))
]

GroovyFX.start {
    new SceneGraphBuilder().stage(visible: true) {
        scene {
            tableView(items: persons) {
                tableColumn(property: "name", text: "Name", prefWidth: 160)
                tableColumn(property: "age", text: "Age", prefWidth: 70)
                tableColumn(property: "gender", text: "Gender", prefWidth: 90)
                tableColumn(property: "dob", text: "Birth", prefWidth: 100,
                    type: Date,
                    converter: { d -> return dateFormat.format(d) })
            }
        }
    }
}
