package com.SQLib;

public class Establecimiento {

    public static final String TABLE_NAME = "ESTABLECIMIENTO";

    public static final String COLUMN_IDEST = "IDEST";
    public static final String COLUMN_NOMBRE_EST = "NOMBRE_EST";
    public static final String COLUMN_ID_SERV = "ID_SERV";
    public static final String COLUMN_DIR_EST = "DIR_EST";
    public static final String COLUMN_TELEFONO_EST = "TELEFONO_EST";
    public static final String COLUMN_EMAIL_EST = "EMAIL_EST";
    public static final String COLUMN_LAT_EST = "LAT_EST";
    public static final String COLUMN_LONG_EST = "LONG_EST";
    public static final String COLUMN_NIV_PRECIO = "NIV_PRECIO";
    public static final String COLUMN_CALIFICACION = "CALIFICACION";

    private String IDEST;
    private String NOMBRE_EST;
    private String ID_SERV;
    private String DIR_EST;
    private String TELEFONO_EST;
    private String EMAIL_EST;
    private String NIV_PRECIO;
    private float CALIFICACION;
    private double LAT_EST;
    private double LONG_EST;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_IDEST + " TEXT PRIMARY KEY,"
                    + COLUMN_NOMBRE_EST + " TEXT,"
                    + COLUMN_ID_SERV + " TEXT,"
                    + COLUMN_DIR_EST + " TEXT,"
                    + COLUMN_TELEFONO_EST + " TEXT,"
                    + COLUMN_EMAIL_EST + " TEXT,"
                    + COLUMN_LAT_EST + " TEXT,"
                    + COLUMN_LONG_EST + " TEXT,"
                    + COLUMN_NIV_PRECIO + " TEXT,"
                    + COLUMN_CALIFICACION + " TEXT"
                    + ")";
    public Establecimiento(){

    }
    public Establecimiento(String IDEST, String NOMBRE_EST, String ID_SERV, String DIR_EST, String  TELEFONO_EST, String EMAIL_EST,
                            double LAT_EST, double LONG_EST, String NIV_PRECIO,  float CALIFICACION){
        this.IDEST = IDEST;
        this.NOMBRE_EST = NOMBRE_EST;
        this.ID_SERV = ID_SERV;
        this.DIR_EST = DIR_EST;
        this.TELEFONO_EST = TELEFONO_EST;
        this.EMAIL_EST = EMAIL_EST;
        this.LAT_EST = LAT_EST;
        this.LONG_EST = LONG_EST;
        this.NIV_PRECIO = NIV_PRECIO;
        this.CALIFICACION = CALIFICACION;

    }

    public String getIDEST() {
        return IDEST;
    }

    public void setIDEST(String IDEST) {
        this.IDEST = IDEST;
    }

    public String getNOMBRE_EST() {
        return NOMBRE_EST;
    }

    public void setNOMBRE_EST(String NOMBRE_EST) {
        this.NOMBRE_EST = NOMBRE_EST;
    }

    public String getID_SERV() {
        return ID_SERV;
    }

    public void setID_SERV(String ID_SERV) {
        this.ID_SERV = ID_SERV;
    }

    public String getDIR_EST() {
        return DIR_EST;
    }

    public void setDIR_EST(String DIR_EST) {
        this.DIR_EST = DIR_EST;
    }

    public String getTELEFONO_EST() {
        return TELEFONO_EST;
    }

    public void setTELEFONO_EST(String TELEFONO_EST) {
        this.TELEFONO_EST = TELEFONO_EST;
    }

    public String getEMAIL_EST() {
        return EMAIL_EST;
    }

    public void setEMAIL_EST(String EMAIL_EST) {
        this.EMAIL_EST = EMAIL_EST;
    }

    public String getNIV_PRECIO() {
        return NIV_PRECIO;
    }

    public void setNIV_PRECIO(String NIV_PRECIO) {
        this.NIV_PRECIO = NIV_PRECIO;
    }

    public float getCALIFICACION() {
        return CALIFICACION;
    }

    public void setCALIFICACION(float CALIFICACION) {
        this.CALIFICACION = CALIFICACION;
    }

    public double getLAT_EST() {
        return LAT_EST;
    }

    public void setLAT_EST(double LAT_EST) {
        this.LAT_EST = LAT_EST;
    }

    public double getLONG_EST() {
        return LONG_EST;
    }

    public void setLONG_EST(double LONG_EST) {
        this.LONG_EST = LONG_EST;
    }
}
