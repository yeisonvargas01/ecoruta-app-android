package com.ecoruta.app.dto;

public class TipoReporteRespuestaDTO {

    private Long idTipoReporte;
    private String nombreTipo;
    private String descripcionTipo;

    public TipoReporteRespuestaDTO() {

    }

    public Long getIdTipoReporte() {
        return idTipoReporte;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }
}