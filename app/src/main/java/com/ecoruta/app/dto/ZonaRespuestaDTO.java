package com.ecoruta.app.dto;

public class ZonaRespuestaDTO {

    private Long idZona;
    private String nombreZona;
    private String tipoZona;
    private String sector;
    private String municipio;
    private String horario;

    public ZonaRespuestaDTO() {

    }

    public Long getIdZona() {
        return idZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public String getTipoZona() {
        return tipoZona;
    }

    public String getSector() {
        return sector;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getHorario() {
        return horario;
    }
}
