package com.example.luis.parcelasapp;

/**
 * Created by mario on 29/01/18.
 */

public class Mriego {

     /*String fecha = jsonObj.getString("Fecha");
           Double Lb = jsonObj.getDouble("Lb");
           Double Tr =  jsonObj.getDouble("Tr");
            String Condicion = jsonObj.getString("Condicion");*/


    private String fecha;


    private Double Lb;

    public Double getLb() {
        return Lb;
    }

    public void setLb(Double Lb) {
        this.Lb = Lb;
    }

    public Double getTr() {
        return Tr;
    }

    public void setTr(Double Tr) {
        this.Tr = Tr;
    }

    public String getCondicion() {
        return Condicion;
    }

    public void setCondicion(String Condicion) {
        this.Condicion = Condicion;
    }
    private Double Tr;
    private  String Condicion;
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



    public Mriego(String fecha, Double Lb, Double Tr, String Condicion) {
        this.fecha = fecha;
        this.Lb = Lb;
        this.Tr = Tr;
        this.Condicion = Condicion;
    }

    public Mriego()
    {

    }

}
