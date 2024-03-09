package com.example.gestionafacil.Models;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mozo {
    @SerializedName("id")
    String id;
    @SerializedName("mozo_nombre")
    String mozo_nombre;
    @SerializedName("mozo_apellidos")
    String mozo_apellidos;
    @SerializedName("ordenado")
    String ordenado;
    private boolean isChecked;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMozo_nombre() {
        return mozo_nombre;
    }

    public void setMozo_nombre(String mozo_nombre) {
        this.mozo_nombre = mozo_nombre;
    }

    public String getMozo_apellido() {
        return mozo_apellidos;
    }

    public void setMozo_apellido(String mozo_apellido) {
        this.mozo_apellidos = mozo_apellido;
    }

    public String getOrdenado() {
        return ordenado;
    }

    public void setOrdenado(String ordenado) {
        this.ordenado = ordenado;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public String tiempoTranscurrido() {
        // Fecha actual
        Date fechaActual = new Date();

        // Formato de fecha para parsear el atributo "ordenado"
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // Parsear la fecha "ordenado" a un objeto Date
            Date fechaOrdenado = formato.parse(ordenado);

            // Calcular la diferencia en milisegundos entre las fechas
            long diferenciaMillis = fechaActual.getTime() - fechaOrdenado.getTime();

            // Convertir los milisegundos a minutos
            long minutosTranscurridos = diferenciaMillis / (60 * 1000);
            // Si la diferencia es menor a un minuto, mostrar "Hace un momento"
            if (diferenciaMillis < 60 * 1000) {
                return "Hace un momento";
            }
            // Formatear la cadena de tiempo transcurrido
            if (minutosTranscurridos < 60) {
                return minutosTranscurridos + " minutos";
            } else {
                long horasTranscurridas = minutosTranscurridos / 60;
                return horasTranscurridas + " horas";
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return "Tiempo desconocido";
        }
    }
}

