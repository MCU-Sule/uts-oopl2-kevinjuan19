/**
 * @author kevin Juan 1972002
 */
package Dao;

import Model.Hutang;
import Model.Player;
import Utility.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class hutangDao implements daoInterface<Hutang>{
    private final Connection conn = JDBCConnection.getConnection();
    @Override
    public int addData(Hutang data) {
        int result = 0;
        try {
            String query = "INSERT INTO hutang (PemberiUtang,Jumlah,Player_id) VALUES (?,?,?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ps.setString(1,data.getPemberiUtang());
            ps.setDouble(2,data.getJumlah());
            ps.setInt(3,data.getPlayer().getId());
            result = ps.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int delData(Hutang data) {
        int result =0;
        try{
            String query = "DELETE FROM hutang WHERE Player_id = ?";
            conn.setAutoCommit(false);
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ps.setInt(1,data.getPlayer().getId());
            result = ps.executeUpdate();
            if (result != 0){
                conn.commit();
            }else{
                conn.rollback();
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int updateData(Hutang data) {
        return 0;
    }

    @Override
    public ObservableList<Hutang> showData() {
        ObservableList<Hutang> hList = FXCollections.observableArrayList();
        try{
            String query = "SELECT h.id as hId, h.PemberiUtang, h.Jumlah, h.Player_id ,p.id, p.Nama , p.Umur, p.Keahlian   FROM hutang h JOIN player p ON h.Player_id = p.id";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                int hId = res.getInt("hId");
                String pemberiUtang = res.getString("PemberiUtang");
                double jumlah = res.getDouble("Jumlah");
                int player_id = res.getInt("Player_id");


                int id = res.getInt("id");
                String nama = res.getString("Nama");
                int umur = res.getInt("Umur");
                String keahlian = res.getString("Keahlian");
                Player player = new Player(id,nama,umur,keahlian);
                Hutang hutang = new Hutang(pemberiUtang,jumlah,player,player_id);
                hList.add(hutang);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return hList;
    }
}
