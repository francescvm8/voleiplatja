package local.example.voleibol;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class parella extends RealmObject {

    @Required
    private final MutableRealmInteger codiParella = MutableRealmInteger.valueOf(0);
    @Required
    private String cognom1;
    @Required
    private String cognom2;
    private int capita;
    @Required
    private String teamColor;

    public String getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    public MutableRealmInteger getCodiParella() {
        return codiParella;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public int getCapita() {
        return capita;
    }

    public void setCapita(int capita) {
        this.capita = capita;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }
}



