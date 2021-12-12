package com.yovix.tp07;

import java.io.Serializable;

public class Product implements Serializable {
    private String ref, design;
    private float prix;
    private int qty;

    public Product(String ref, String design, float prix, int qty) {
        this.ref = ref;
        this.design = design;
        this.prix = prix;
        this.qty = qty;
    }

    public Product() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


    @Override
    public String toString() {
        return ref + " : " + design;
    }

      /*
    @Override
    public String toString() {
        return ref + " : " + design+ " : " + prix+ " : " + qty;
    }

     */
}
