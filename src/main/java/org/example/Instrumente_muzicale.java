package org.example;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Instrumente_muzicale {
    private final IntegerProperty id;
    private final StringProperty tip;
    private final StringProperty producator;
    private final StringProperty model;
    private final IntegerProperty nr_corzi;

    public Instrumente_muzicale(int id, String tip, String producator, String model, int nr_corzi) {
        this.id = new SimpleIntegerProperty(id);
        this.tip = new SimpleStringProperty(tip);
        this.producator = new SimpleStringProperty(producator);
        this.model = new SimpleStringProperty(model);
        this.nr_corzi = new SimpleIntegerProperty(nr_corzi);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTip() {
        return tip.get();
    }

    public void setTip(String tip) {
        this.tip.set(tip);
    }

    public StringProperty tipProperty() {
        return tip;
    }

    public String getProducator() {
        return producator.get();
    }

    public void setProducator(String producator) {
        this.producator.set(producator);
    }

    public StringProperty producatorProperty() {
        return producator;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public StringProperty modelProperty() {
        return model;
    }

    public int getNr_corzi() {
        return nr_corzi.get();
    }

    public void setNr_corzi(int nr_corzi) {
        this.nr_corzi.set(nr_corzi);
    }

    public IntegerProperty nr_corziProperty() {
        return nr_corzi;
    }

    @Override
    public String toString() {
        return "Instrument: " + tip.get() + "\n" +
                "Producator: " + producator.get() + "\n" +
                "Model: " + model.get() + "\n" +
                "Nr_corzi: " + nr_corzi.get() ;
    }
}
