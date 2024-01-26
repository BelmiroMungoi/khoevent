package com.bbm.khoevent.utils;

import java.time.LocalDate;

public class EmailUtils {

    public static String getEmailMessage(String name, String eventName, LocalDate date) {
        return "Olá " + name + ",\n\nO seu ticket para o evento: "+ eventName
                +" foi criado com sucesso." +
                "\n O evento irá ocorrer no dia: " + date
                +"\n Save the date." +
                "\n\nA equipe de Suporte.";
    }
}
