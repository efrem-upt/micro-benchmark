module edu.poli.efrem.microbenchmark {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.management;

    opens edu.poli.efrem.microbenchmark to javafx.fxml;
    exports edu.poli.efrem.microbenchmark;
}