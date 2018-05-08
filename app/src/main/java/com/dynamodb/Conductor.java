package com.dynamodb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "Conductor")
public class Conductor {

    String Username;
    String CorreoE;
    String Nombre;

    @DynamoDBHashKey(attributeName = "Username")
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = Username;
    }

    @DynamoDBAttribute(attributeName = "CorreoE")
    public String getCorreoE() {
        return CorreoE;
    }

    public void setCorreoE(String CorreoE) {
        this.CorreoE = CorreoE;
    }

    @DynamoDBAttribute(attributeName = "Nombre")
    public String getNombre(){return Nombre;}

    public void setNombre(String nombre){this.Nombre = nombre;}



}
