package com.roche.appClient.AppClient.constant;

public enum Constant {

    SUCCESS("Exito"),
    MESSAGE_SECCESS("Ejecucion Realizada Correctamente"),
    ERROR("Error"),
    MESSAGE_ERROR("Ocurrio un Error! Fallido"),
    MESSAGE_EMPTY_ELEMENTS("No Existen Valores");

    private String message;

    private Constant(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
