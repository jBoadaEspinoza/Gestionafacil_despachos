package com.example.gestionafacil.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class DespachoResponse {

    @SerializedName("response")
    private ResponseData response;

    public ResponseData getResponse() {
        return response;
    }

    public static class ResponseData {
        @SerializedName("success")
        private boolean success;
        @SerializedName("data")
        private List<Despacho> data;
        @SerializedName("token_actualizado")
        private String tokenActualizado;

        public boolean isSuccess() {
            return success;
        }



        public List<Despacho> getData() {
            return data;
        }



        public String getTokenActualizado() {
            return tokenActualizado;
        }
    }
}
