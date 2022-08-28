module com.wgu.c482 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens com.wgu.c482 to javafx.fxml;
    exports com.wgu.c482;
}