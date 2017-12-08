package com.example.luis.parcelasapp.modelo;

/**
 * Created by mario on 8/12/17.
 */

public class MresumenRiego {
    private String condicion;
    private String fecha;
    private double lb;
    private double tr;

    public MresumenRiego(String condicion, String fecha, double lb, double tr) {
        this.setCondicion(condicion);
        this.setFecha(fecha);
        this.setLb(lb);
        this.setTr(tr);
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getLb() {
        return lb;
    }

    public void setLb(double lb) {
        this.lb = lb;
    }

    public double getTr() {
        return tr;
    }

    public void setTr(double tr) {
        this.tr = tr;
    }
}
