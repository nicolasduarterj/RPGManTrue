module com.nduarte.rpgmantrue {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.nduarte.rpgmantrue to javafx.fxml;
    opens com.nduarte.rpgmantrue.controllers to javafx.fxml;
    exports com.nduarte.rpgmantrue;
}
