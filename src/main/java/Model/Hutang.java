/**
 * @author kevin Juan 1972002
 */
package Model;

public class Hutang {
    private int id;
    private String pemberiUtang;
    private double jumlah;
    private Player player;
    private int player_id;

    public Hutang(String pemberiUtang, double jumlah, Player player, int player_id) {

        this.pemberiUtang = pemberiUtang;
        this.jumlah = jumlah;
        this.player = player;
        this.player_id = player_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPemberiUtang() {
        return pemberiUtang;
    }

    public void setPemberiUtang(String pemberiUtang) {
        this.pemberiUtang = pemberiUtang;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}
