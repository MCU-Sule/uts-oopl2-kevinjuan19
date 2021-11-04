/**
 * @author kevin Juan 1972002
 */
package Dao;

import Model.Player;
import Utility.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class playerDao implements daoInterface<Player>{
    private final Connection conn = JDBCConnection.getConnection();
    @Override
    public int addData(Player data) {
        int result = 0;
        try {
            String query = "INSERT INTO player (id,Nama,Umur,Keahlian) VALUES (?,?,?,?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ps.setInt(1,data.getId());
            ps.setString(2,data.getNama());
            ps.setInt(3,data.getUmur());
            ps.setString(4,data.getKeahlian());
            result = ps.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int delData(Player data) {
        int result =0;
        try{
            String query = "DELETE FROM player WHERE id = ?";
            conn.setAutoCommit(false);
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ps.setInt(1,data.getId());
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
    public int updateData(Player data) {
        int result = 0;
        try {
            Connection connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            String query = "UPDATE player SET Nama=?, Umur=?, Keahlian=? WHERE id=? ";
            PreparedStatement ps = JDBCConnection.getConnection().prepareStatement(query);
            ps.setString(1, data.getNama());
            ps.setInt(2,data.getUmur());
            ps.setString(3,data.getKeahlian());
            ps.setInt(4,data.getId());
            result = ps.executeUpdate();
            if (result!=0){
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public ObservableList<Player> showData() {
        ObservableList<Player> pList = FXCollections.observableArrayList();
        try{
            String query = "SELECT * FROM player";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                int id = res.getInt("id");
                String nama = res.getString("Nama");
                int umur = res.getInt("Umur");
                String keahlian = res.getString("Keahlian");
                Player player = new Player(id,nama,umur,keahlian);
                pList.add(player);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return pList;
    }
}
