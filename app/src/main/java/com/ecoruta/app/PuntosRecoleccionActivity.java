package com.ecoruta.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecoruta.app.adaptador.ZonaAdaptador;
import com.ecoruta.app.dto.ZonaRespuestaDTO;
import com.ecoruta.app.red.ClienteApi;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PuntosRecoleccionActivity
        extends AppCompatActivity {

    private RecyclerView listaZonas;
    private ProgressBar indicadorCarga;
    private TextView textoEstado;
    private MaterialButton botonReintentar;

    private ZonaAdaptador zonaAdaptador;

    private Call<List<ZonaRespuestaDTO>>
            solicitudZonas;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(
                R.layout.activity_puntos_recoleccion
        );

        configurarBordesPantalla();
        inicializarComponentes();
        configurarLista();
        configurarEventos();
        consultarZonas();
    }

    private void configurarBordesPantalla() {

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (vista, insets) -> {

                    Insets barrasSistema =
                            insets.getInsets(
                                    WindowInsetsCompat.Type
                                            .systemBars()
                            );

                    vista.setPadding(
                            barrasSistema.left + 18,
                            barrasSistema.top + 14,
                            barrasSistema.right + 18,
                            barrasSistema.bottom + 14
                    );

                    return insets;
                }
        );
    }

    private void inicializarComponentes() {

        listaZonas =
                findViewById(
                        R.id.listaZonas
                );

        indicadorCarga =
                findViewById(
                        R.id.indicadorCarga
                );

        textoEstado =
                findViewById(
                        R.id.textoEstado
                );

        botonReintentar =
                findViewById(
                        R.id.btnReintentar
                );
    }

    private void configurarLista() {

        zonaAdaptador =
                new ZonaAdaptador();

        listaZonas.setLayoutManager(
                new LinearLayoutManager(this)
        );

        listaZonas.setAdapter(
                zonaAdaptador
        );

        listaZonas.setHasFixedSize(true);
    }

    private void configurarEventos() {

        MaterialButton botonVolver =
                findViewById(
                        R.id.btnVolver
                );

        botonVolver.setOnClickListener(
                vista -> finish()
        );

        botonReintentar.setOnClickListener(
                vista -> consultarZonas()
        );
    }

    private void consultarZonas() {

        cancelarSolicitudAnterior();
        mostrarCargando();

        solicitudZonas =
                ClienteApi
                        .obtenerInstancia()
                        .consultarZonas();

        solicitudZonas.enqueue(
                new Callback<>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<ZonaRespuestaDTO>> call,
                            @NonNull Response<List<ZonaRespuestaDTO>>
                                    response) {

                        if (!response.isSuccessful()) {
                            mostrarError();
                            return;
                        }

                        List<ZonaRespuestaDTO> zonas =
                                response.body();

                        if (zonas == null
                                || zonas.isEmpty()) {

                            mostrarListaVacia();
                            return;
                        }

                        mostrarZonas(zonas);
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<ZonaRespuestaDTO>> call,
                            @NonNull Throwable throwable) {

                        if (call.isCanceled()) {
                            return;
                        }

                        mostrarError();
                    }
                }
        );
    }

    private void mostrarCargando() {

        indicadorCarga.setVisibility(
                View.VISIBLE
        );

        textoEstado.setVisibility(
                View.VISIBLE
        );

        textoEstado.setText(
                R.string.cargando_puntos
        );

        botonReintentar.setVisibility(
                View.GONE
        );

        listaZonas.setVisibility(
                View.GONE
        );
    }

    private void mostrarZonas(
            List<ZonaRespuestaDTO> zonas) {

        indicadorCarga.setVisibility(
                View.GONE
        );

        textoEstado.setVisibility(
                View.GONE
        );

        botonReintentar.setVisibility(
                View.GONE
        );

        listaZonas.setVisibility(
                View.VISIBLE
        );

        zonaAdaptador.submitList(
                new ArrayList<>(zonas)
        );
    }

    private void mostrarListaVacia() {

        indicadorCarga.setVisibility(
                View.GONE
        );

        listaZonas.setVisibility(
                View.GONE
        );

        textoEstado.setVisibility(
                View.VISIBLE
        );

        textoEstado.setText(
                R.string.sin_puntos_disponibles
        );

        botonReintentar.setVisibility(
                View.VISIBLE
        );
    }

    private void mostrarError() {

        indicadorCarga.setVisibility(
                View.GONE
        );

        listaZonas.setVisibility(
                View.GONE
        );

        textoEstado.setVisibility(
                View.VISIBLE
        );

        textoEstado.setText(
                R.string.error_consultar_puntos
        );

        botonReintentar.setVisibility(
                View.VISIBLE
        );
    }

    private void cancelarSolicitudAnterior() {

        if (solicitudZonas != null
                && !solicitudZonas.isCanceled()) {

            solicitudZonas.cancel();
        }
    }

    @Override
    protected void onDestroy() {

        cancelarSolicitudAnterior();
        super.onDestroy();
    }
}