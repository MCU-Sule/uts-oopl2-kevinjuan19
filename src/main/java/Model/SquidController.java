/**
 * @author kevin Juan 1972002
 */
package Model;

import Dao.hutangDao;
import Dao.playerDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SquidController {
    @FXML
    private HBox utama;

    @FXML
    private TableView<Player> tablePemain;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNama;

    @FXML
    private TableColumn<?, ?> colUmur;

    @FXML
    private TableColumn<?, ?> colKemampuan;

    @FXML
    private ComboBox<Player> cmbPeserta;

    @FXML
    private TextField txtPemberiUtang;

    @FXML
    private TextField txtJumlah;

    @FXML
    private TableView<Hutang> tableHutang;

    @FXML
    private TableColumn<?, ?> colIdPemain;

    @FXML
    private TableColumn<?, ?> colHutangTer;

    @FXML
    private TableColumn<?, ?> colSejumlah;

    @FXML
    private Button hapusHutang;

    private Hutang hSelected ;
    private Player pSelected;

    private playerDao pDao = new playerDao();
    private ObservableList<Player> pList = pDao.showData();

    public ObservableList<Player> getpList() {
        return pList;
    }
    boolean add;

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isAdd() {
        return add;
    }

    private hutangDao hDao = new hutangDao();
    private ObservableList<Hutang> hList = hDao.showData();

    public void initialize() {
        tablePemain.setItems(pList);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colUmur.setCellValueFactory(new PropertyValueFactory<>("umur"));
        colKemampuan.setCellValueFactory(new PropertyValueFactory<>("keahlian"));

        tableHutang.setItems(hList);
        colIdPemain.setCellValueFactory(new PropertyValueFactory<>("player_id"));
        colHutangTer.setCellValueFactory(new PropertyValueFactory<>("pemberiUtang"));
        colSejumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));

        cmbPeserta.setItems(pList);
        cmbPeserta.getSelectionModel().select(0);

        tableHutang.setOnMouseClicked(e -> tableUpdate());
        tablePemain.setOnMouseClicked(e -> tableUpdate2());

    }
    private void tableUpdate(){
        hSelected = tableHutang.getSelectionModel().getSelectedItem();
        if (hSelected != null){
            txtJumlah.setText(String.valueOf(hSelected.getJumlah()));
            txtPemberiUtang.setText(hSelected.getPemberiUtang());
            cmbPeserta.setValue(hSelected.getPlayer());
        }

    }
    public Player tableUpdate2(){
        pSelected = tablePemain.getSelectionModel().getSelectedItem();
        return pSelected;
    }
    public TableView<Player> getTablePemain() {
        return tablePemain;
    }

    @FXML
    void addPemain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModalPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        StageModalController c2 = fxmlLoader.getController();
        c2.setSquidController(this);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(utama.getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void editPemain(ActionEvent event) throws IOException {
        setAdd(true);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModalPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        StageModalController c2 = fxmlLoader.getController();
        c2.setSquidController(this);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(utama.getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void hapusPemain(ActionEvent event) {
        if (pSelected != null){
            int result = pDao.delData(pSelected);
            if (result != 0){
                System.out.println("DELETE SUCCESS!");
            }
            pList.clear();
            pList.addAll(pDao.showData());
            tablePemain.refresh();
            clearAll();
        }
    }


    @FXML
    void tambahHutang(ActionEvent event) {
        if (txtPemberiUtang.getText().isEmpty()){
            showAlert("Please fill in Field");
        }else if(cmbPeserta.getSelectionModel().isEmpty()){
            showAlert("Please fill in Field");
        }else if(txtJumlah.getText().isEmpty()){
            showAlert("Please fill in Field");
        } else{
            try {
                String pemberiUtang = txtPemberiUtang.getText();
                double jumlah =Double.parseDouble(txtJumlah.getText());
                Player player = cmbPeserta.getValue();
                Hutang hutang = new Hutang(pemberiUtang,jumlah,player, player.getId());
                hDao.addData(hutang);
                hList.clear();
                hList.addAll(hDao.showData());
                tableHutang.refresh();
                clearAll();
            }
            catch (NumberFormatException ex){
                showAlert("Please Input ID field with Number!");
            }

        }
    }

    public void clearAll(){
        txtJumlah.clear();
        txtPemberiUtang.clear();
    }

    public void showAlert(String kalimat){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.show();
        alert.setContentText(kalimat);
    }

    public void hapusHutang(ActionEvent event) {
        if (hSelected != null){
            int result = hDao.delData(hSelected);
            if (result != 0){
                System.out.println("DELETE SUCCESS!");
            }
            hList.clear();
            hList.addAll(hDao.showData());
            tableHutang.refresh();
            clearAll();
        }


    }
}
