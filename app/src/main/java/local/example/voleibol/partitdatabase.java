package local.example.voleibol;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class partitdatabase extends RealmObject {


    @Required
    private final MutableRealmInteger codiPartit = MutableRealmInteger.valueOf(0);
    private parella parella1;
    private parella parella2;
    @Required
    private String genere;
    //private

    public MutableRealmInteger getCodiPartit() {
        return codiPartit;
    }

    public parella getParella1() {
        return parella1;
    }

    public void setParella1(parella parella1) {
        this.parella1 = parella1;
    }

    public parella getParella2() {
        return parella2;
    }

    public void setParella2(parella parella2) {
        this.parella2 = parella2;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}




