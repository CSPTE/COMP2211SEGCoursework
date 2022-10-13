module com.example.segproject {
    requires transitive javafx.controls;
    requires java.desktop;
    requires javafx.swing;

    exports com.example.segproject;
    exports com.example.segproject.scenes;
    exports com.example.segproject.components;
    exports com.example.segproject.events;
}