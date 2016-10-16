package binding

import groovyx.javafx.beans.FXBindable

@FXBindable
class Person {
    String name;
    int age;
    String gender;
    Date dob;
}
