package compiladores3.controller;

import compiladores3.model.AutomatoThomp;
import compiladores3.model.Estado;
import compiladores3.model.Simbolo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class JanelaPrincipalController implements Initializable {

    @FXML
    public TextField tfInfixa;
    @FXML
    public TextField tfPosfixa;
    @FXML
    public Button gerar;
    @FXML
    public BorderPane panel;
    @FXML
    public Label log;

    private TableView tv;
    private ObservableList<Estado> obEstados;
//    @FXML

    public void gerar() {
        Conversor c = new Conversor();
        try {
            if (tfInfixa.getText().compareTo("") != 0) {
                tfPosfixa.setText(c.converter(tfInfixa.getText()));
            }
            AutomatoThomp result = c.calcAutomato(tfPosfixa.getText());
            obEstados = FXCollections.observableArrayList(result.Q);
            tv = new TableView();
            tv.setItems(obEstados);
            TableColumn<Estado, String> tc = new TableColumn("Q's");
            tc.setCellValueFactory(a -> a.getValue().toStringProp());
            tv.getColumns().add(tc);
            for (Simbolo s : result.E.getAlfabeto()) {
                if (s.getSimbolo() != '&') {
                    tc = new TableColumn(s.toString());
                    tc.setCellValueFactory(a -> a.getValue().getTransicoesProp(s.getSimbolo()));
                    tv.getColumns().add(tc);
                }
            }
            tc = new TableColumn("&");
            tc.setCellValueFactory(a -> a.getValue().getTransicoesProp('&'));
            tv.getColumns().add(tc);

            panel.setCenter(tv);

            log.setText("");
        } catch (Throwable e) {
            log.setText("Algo errado não está certo");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
