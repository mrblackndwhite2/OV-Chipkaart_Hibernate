package domein;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Adres {
    @Id
    @Column(name = "adres_id")
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    public Adres(int id, String postcode, String straat, String huisnummer, String woonplaats) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

    public Adres(int id, String postcode, String straat, String huisnummer, String woonplaats, Reiziger reiziger) {
        this(id, postcode, straat, huisnummer, woonplaats);
        this.reiziger = reiziger;
    }

    public Adres() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        if (reiziger != null) {
            return String.format("(#%d) %s %s, %S %S van reiziger %d",
                    id, straat, huisnummer, postcode, woonplaats, reiziger.getId());
        } else {
            return String.format("(#%d) %s %s, %S %S van reiziger %d",
                    id, straat, huisnummer, postcode, woonplaats, 0);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adres adres = (Adres) o;
        return id == adres.id &&
                postcode.equals(adres.postcode) &&
                huisnummer.equals(adres.huisnummer) &&
                straat.equals(adres.straat) &&
                woonplaats.equals(adres.woonplaats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postcode, huisnummer, straat, woonplaats);
    }
}
