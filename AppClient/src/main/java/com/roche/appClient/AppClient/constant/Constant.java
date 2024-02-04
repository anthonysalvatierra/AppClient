package com.roche.appClient.AppClient.constant;

public enum Constant {

    CLIENTS("clients"),

    CLIENT("client"),

    CLIENT_DELETED("clientDeleted"),
    MESSAGE_SECCESS("Ejecucion Realizada Correctamente"),
    MESSAGE_ERROR("Ocurrio un Error! Fallido"),
    MESSAGE_EMPTY_ELEMENTS("No Existen Valores"),

    MESSAGE("message"),

    SENTENCE_ERROR("ha ocurrido un error al momento de ejecutar la sentencia "),

    ELEMENT_DOES_NOT_EXIST("el elemento no existe en la base de datos");

    private String message;

    private Constant(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
