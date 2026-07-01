package com.ecoruta.app.red;

import com.ecoruta.app.dto.TipoReporteRespuestaDTO;
import com.ecoruta.app.dto.ZonaRespuestaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EcoRutaApi {

    @GET("zonas")
    Call<List<ZonaRespuestaDTO>> consultarZonas();

    @GET("tipos-reporte")
    Call<List<TipoReporteRespuestaDTO>> consultarTiposReporte();
}
