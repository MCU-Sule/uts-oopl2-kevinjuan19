/**
 * @author kevin Juan 1972002
 */
package Model;

import Dao.playerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StageModalController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNama;

    @FXML
    private TextField txtUmur;

    @FXML
    private TextField txtKeahlian;

    private SquidController squidController;
    private playerDao pDao;


    public void setSquidController(SquidController squidController) {
        this.squidController = squidController;
        if (squidController.isAdd()){
            txtId.setText( String.valueOf(squidController.getTablePemain().getSelectionModel().getSelectedItem().getId()));
            txtUmur.setText(String.valueOf(squidController.getTablePemain().getSelectionModel().getSelectedItem().getUmur()));
            txtNama.setText(squidController.getTablePemain().getSelectionModel().getSelectedItem().getNama());
            txtKeahlian.setText(squidController.getTablePemain().getSelectionModel().getSelectedItem().getKeahlian());

        }

    }

    @FXML
    void btnCancel(ActionEvent event) {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnOk(ActionEvent event) {
        if (squidController.isAdd()){
            if (txtId.getText().isEmpty()){
                showAlert("Please fill in  Field");
            }else if(txtNama.getText().isEmpty()){
                showAlert("Please fill in  Field");
            }else if(txtUmur.getText().isEmpty()){
                showAlert("Please fill in  Field");
            }else if(txtKeahlian.getText().isEmpty()){
                showAlert("Please fill in  Field");
            }else{
                try {
                        int id = Integer.parseInt(txtId.getText());
                        String nama = txtNama.getText();
                        int umur = Integer.parseInt(txtUmur.getText());
                        String keahlian = txtKeahlian.getText();
                        Player items = new Player(id,nama,umur,keahlian);
                        int result = pDao.updateData(items);
                    squidController.getpList().clear();
                    squidController.getpList().addAll(pDao.showData());
                        if (result != 0) {
                            System.out.println("UPDATE SUCCESS");
                        }
                    clearAll();
                }
                catch (NumberFormatException ex){
                    showAlert("Please Input ID field with Number!");
                }
            }
        }else {
            if (txtId.getText().isEmpty()){
                showAlert("Please fill in  Field");
            }else if(txtNama.getText().isEmpty()){
                showAlert("Please fill in  Field");
            }else if(txtUmur.getText().isEmpty()){
                showAlert("Please fill in  Field");
            }else if(txtKeahlian.getText().isEmpty()){
                showAlert("Please fill in  Field");
            }else{
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String nama = txtNama.getText();
                    int umur = Integer.parseInt(txtUmur.getText());
                    String keahlian = txtKeahlian.getText();
                    boolean ada = false;
                    for (int i = 0; i < squidController.getpList().size(); i++){
                        if(nama.equals( squidController.getpList().get(i).getNama())) {
                            ada = true;
                            break;
                        }
                    }
                    if (ada){
                        showAlert("Data Already Exist!");
                    }else{
                        Player player = new Player(id,nama,umur,keahlian);
                        pDao.addData(player);
                        squidController.getpList().clear();
                        squidController.getpList().addAll(pDao.showData());
                    }
                    clearAll();
                }
                catch (NumberFormatException ex){
                    showAlert("Please Input ID field with Number!");
                }
            }
        }
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();

    }
    public void showAlert(String kalimat){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.show();
        alert.setContentText(kalimat);
    }
    public void clearAll(){
        txtId.clear();
        txtKeahlian.clear();
        txtNama.clear();
        txtUmur.clear();
    }
    @FXML
    public void initialize(){
        pDao = new playerDao();
    }

}
