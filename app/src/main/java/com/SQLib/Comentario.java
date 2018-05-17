package com.SQLib;

public class Comentario {

    public static final String TABLE_NAME = "COMENTARIO";

    public static final String COLUMN_IDCOM = "IDCOM";
    public static final String COLUMN_COMENTARIO = "COMENTARIO";
    public static final String COLUMN_COND_USER = "COND_USER";
    public static final String COLUMN_ID_EST = "ID_EST";

    private String IDCOM;
    private String COMENTARIO;
    private String COND_USER;
    private String ID_EST;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_IDCOM + " TEXT PRIMARY KEY,"
                    + COLUMN_COMENTARIO + " TEXT,"
                    + COLUMN_COND_USER + " TEXT,"
                    + COLUMN_ID_EST + " TEXT"
                    + ")";

    public Comentario(){

    }

    public Comentario(String IDCOM, String COMENTARIO, String COND_USER, String ID_EST){
        this.IDCOM = IDCOM;
        this.COMENTARIO = COMENTARIO;
        this.COND_USER = COND_USER;
        this.ID_EST = ID_EST;
    }

    public String getIDCOM() {
        return IDCOM;
    }

    public void setIDCOM(String IDCOM) {
        this.IDCOM = IDCOM;
    }

    public String getCOMENTARIO() {
        return COMENTARIO;
    }

    public void setCOMENTARIO(String COMENTARIO) {
        this.COMENTARIO = COMENTARIO;
    }

    public String getCOND_USER() {
        return COND_USER;
    }

    public void setCOND_USER(String COND_USER) {
        this.COND_USER = COND_USER;
    }

    public String getID_EST() {
        return ID_EST;
    }

    public void setID_EST(String ID_EST) {
        this.ID_EST = ID_EST;
    }
}
