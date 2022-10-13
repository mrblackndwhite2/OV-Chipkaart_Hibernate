package domein;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Product {
    @Id
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(mappedBy = "producten")
    private List<OVChipkaart> koppelingen;

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.koppelingen = new ArrayList<OVChipkaart>();
    }

    public Product() {

    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
        onChange();
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
        onChange();
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
        onChange();
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
        onChange();
    }

    public List<OVChipkaart> getKoppelingen() {
        return koppelingen;
    }

    void ovUpdate(OVChipkaart ov) {
        for (int i = 0; i < koppelingen.size(); i++) {
            if (koppelingen.get(i).getKaart_nummer() == ov.getKaart_nummer()) {
                koppelingen.set(i, ov);
                return;
            }
        }
    }

    private void onChange() {
        for (OVChipkaart ov : koppelingen) {
            ov.productUpdate(this);
        }
    }

    // tries to add ov. To update ov, do tryDelete first, then tryAdd it back
    public boolean tryAddOv(OVChipkaart ov) {
        if (ov == null) {
            return false;
        }

        if (koppelingen.contains(ov)) {
//            System.out.println("OV-chipkaart al gekoppeld");
            return false;
        }

        for (int i = 0; i < koppelingen.size(); i++) {
            // case: product al toegevoegd maar aangepast
            if (koppelingen.get(i).getKaart_nummer() == ov.getKaart_nummer()) {
                koppelingen.remove(i);
                koppelingen.add(ov);
//                System.out.println("ov succesvol gekoppeld aan product");
                ov.tryAddProduct(this);
                onChange();
                return true;
            }
        }

        koppelingen.add(ov);
//        System.out.println("ov succesvol gekoppeld aan product");
        ov.tryAddProduct(this);
        onChange();
        return true;
    }

    // Deletes ov with same kaartnummer as arg
    public boolean tryDeleteOv(OVChipkaart ov) {
        if (koppelingen.isEmpty()) {
            return false;
        }

        if (ov == null) {
            return false;
        }

        if (koppelingen.contains(ov)) {
            koppelingen.remove(ov);
            ov.tryDeleteProduct(this);
            onChange();
            return true;
        }

        for (int i = 0; i < koppelingen.size(); i++) {
            if (koppelingen.get(i).getKaart_nummer() == ov.getKaart_nummer()) {
                koppelingen.remove(i);
                ov.tryDeleteProduct(this);
                onChange();
                return true;
            }
        }

        return false;
    }


    @Override
    public String toString() {
        String result = "Product #%d %s : %s (%.2f)\nlinkedOV: [";
        if (koppelingen != null) {
            for (OVChipkaart ov : koppelingen) {
                result += ov.getKaart_nummer();
                result += ", ";
            }
        }
        result += "]";
        return String.format(result, product_nummer, naam, beschrijving, prijs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return product_nummer == product.product_nummer &&
                Double.compare(product.prijs, prijs) == 0 &&
                naam.equals(product.naam) &&
                beschrijving.equals(product.beschrijving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_nummer, naam, beschrijving, prijs);
    }
}
