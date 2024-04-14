package org.example;

import javafx.beans.property.*;

public class Cantareti {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nume = new SimpleStringProperty();
    private final IntegerProperty varsta = new SimpleIntegerProperty();
    private final ObjectProperty<Instrumente_muzicale> id_instrument = new SimpleObjectProperty<>();

    public Cantareti() {
        // Constructor implicit
    }

    public Cantareti(int id, String nume, int varsta, Instrumente_muzicale id_instrument) {
        this.id.set(id);
        this.nume.set(nume);
        this.varsta.set(varsta);
        this.id_instrument.set(id_instrument);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNume() {
        return nume.get();
    }

    public StringProperty numeProperty() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume.set(nume);
    }

    public int getVarsta() {
        return varsta.get();
    }

    public IntegerProperty varstaProperty() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta.set(varsta);
    }

    public Instrumente_muzicale getId_instrument() {
        return id_instrument.get();
    }

    public ObjectProperty<Instrumente_muzicale> id_instrumentProperty() {
        return id_instrument;
    }

    public void setId_instrument(Instrumente_muzicale id_instrument) {
        this.id_instrument.set(id_instrument);
    }

    @Override
    public String toString() {
        return "Cântăreț: " + nume.get() + "\n" +
                "Varsta: " + varsta.get() + "\n" +
                "Instrumente: " + id_instrument.get().getTip();
    }
}
