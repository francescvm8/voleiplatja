package local.example.voleibol;

import java.io.Serializable;

public class parellaDatabase implements Serializable {


    private String codiParella;
    private String cognom1;
    private String cognom2;
    private String capita;
    private String teamColor;
    private String sacador;

    //Constructor sencer
    public parellaDatabase(String codiParella, String cognom1, String cognom2, String capita, String teamColor, String sacador) {
        this.codiParella = codiParella;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.capita = capita;
        this.teamColor = teamColor;
        this.sacador = sacador;
    }
    //Constructor pre partit
    public parellaDatabase(String codiParella, String cognom1, String cognom2) {
        this.codiParella = codiParella;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
    }

    //Constructor sense dades
    public parellaDatabase() {
    }

    public String getSacador() {
        return sacador;
    }

    public void setSacador(String sacador) {
        this.sacador = sacador;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    public String getCodiParella() {
        return codiParella;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCapita() {
        return capita;
    }

    public void setCapita(String capita) {
        this.capita = capita;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }
}



