package local.example.voleibol;

import java.io.Serializable;


public class partitdatabase implements Serializable {

    private parellaDatabase parellaDatabase1;
    private parellaDatabase parellaDatabase2;
    private String set1;
    private String set2;
    private String set3;
    private String horaInici;
    private String horaFinal;
    private String arbitre1;
    private String arbitre2;
    private String anotador;
    private String pista;
    private boolean estatPartit;


    //Constructor sencer
    public partitdatabase(parellaDatabase parellaDatabase1, parellaDatabase parellaDatabase2, String set1, String set2, String set3, String horaInici, String horaFinal, String arbitre1, String arbitre2, String anotador, String pista, boolean estatPartit) {
        this.parellaDatabase1 = parellaDatabase1;
        this.parellaDatabase2 = parellaDatabase2;
        this.set1 = set1;
        this.set2 = set2;
        this.set3 = set3;
        this.horaInici = horaInici;
        this.horaFinal = horaFinal;
        this.arbitre1 = arbitre1;
        this.arbitre2 = arbitre2;
        this.anotador = anotador;

        this.pista=pista;
        this.estatPartit = estatPartit;
    }

    //Constructor partit sense inciar i 1 arbitre
    public partitdatabase(parellaDatabase parellaDatabase1, parellaDatabase parellaDatabase2, String horaInici, String arbitre1, String pista) {
        this.parellaDatabase1 = parellaDatabase1;
        this.parellaDatabase2 = parellaDatabase2;
        this.horaInici = horaInici;
        this.arbitre1 = arbitre1;

        this.pista= pista;
        this.estatPartit = false;
    }

    //Constructor partit sense inciar i 3 arbitres
    public partitdatabase(parellaDatabase parellaDatabase1, parellaDatabase parellaDatabase2, String horaInici, String arbitre1, String arbitre2, String anotador, String pista) {
        this.parellaDatabase1 = parellaDatabase1;
        this.parellaDatabase2 = parellaDatabase2;
        this.horaInici = horaInici;
        this.arbitre1 = arbitre1;
        this.arbitre2 = arbitre2;
        this.anotador = anotador;

        this.pista= pista;
        this.estatPartit = false;
    }

    //Constructor sense dades
    public partitdatabase() {
    }

    public boolean isEstatPartit() {
        return estatPartit;
    }

    public void setEstatPartit(boolean estatPartit) {
        this.estatPartit = estatPartit;
    }

    public parellaDatabase getParellaDatabase1() {
        return parellaDatabase1;
    }

    public void setParellaDatabase1(parellaDatabase parellaDatabase1) {
        this.parellaDatabase1 = parellaDatabase1;
    }

    public parellaDatabase getParellaDatabase2() {
        return parellaDatabase2;
    }

    public void setParellaDatabase2(parellaDatabase parellaDatabase2) {
        this.parellaDatabase2 = parellaDatabase2;
    }

    public String getSet1() {
        return set1;
    }

    public void setSet1(String set1) {
        this.set1 = set1;
    }

    public String getSet2() {
        return set2;
    }

    public void setSet2(String set2) {
        this.set2 = set2;
    }

    public String getSet3() {
        return set3;
    }

    public void setSet3(String set3) {
        this.set3 = set3;
    }

    public String getHora() {
        return horaInici;
    }

    public void setHora(String hora) {
        this.horaInici = hora;
    }

    public String getArbitre1() {
        return arbitre1;
    }

    public void setArbitre1(String arbitre1) {
        this.arbitre1 = arbitre1;
    }

    public String getArbitre2() {
        return arbitre2;
    }

    public void setArbitre2(String arbitre2) {
        this.arbitre2 = arbitre2;
    }

    public String getAnotador() {
        return anotador;
    }

    public void setAnotador(String anotador) {
        this.anotador = anotador;
    }

    public String getHoraInici() {
        return horaInici;
    }

    public void setHoraInici(String horaInici) {
        this.horaInici = horaInici;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }
}




