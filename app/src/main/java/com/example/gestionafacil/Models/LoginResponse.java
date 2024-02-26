package com.example.gestionafacil.Models;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("response")
    private ResponseData response;

    public ResponseData getResponse() {
        return response;
    }

    public void setResponse(ResponseData response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return response != null && response.isSuccess();
    }

    public String getMessage() {
        return response != null ? response.getMessage() : null;
    }

    public class ResponseData {
        @SerializedName("success")
        private boolean success;

        @SerializedName("message")
        private String message;

        @SerializedName("data")
        private Data data;


        @SerializedName("token")
        private String token;

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public Data getData() {
            return data;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public class Data {
        @SerializedName("establecimiento")
        private Establecimiento establecimiento;

        @SerializedName("usuario")
        private Usuario usuario;

        public Establecimiento getEstablecimiento() {
            return establecimiento;
        }

        public Usuario getUsuario() {
            return usuario;
        }
    }

}
