package com.ecoruta.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(
                R.layout.activity_main
        );

        configurarBordesPantalla();
        configurarEventos();
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
                            barrasSistema.left,
                            barrasSistema.top,
                            barrasSistema.right,
                            barrasSistema.bottom
                    );

                    return insets;
                }
        );
    }

    private void configurarEventos() {

        MaterialButton botonClasificar =
                findViewById(
                        R.id.btnClasificar
                );

        MaterialButton botonPuntos =
                findViewById(
                        R.id.btnPuntos
                );

        botonClasificar.setOnClickListener(
                vista -> abrirClasificacion()
        );

        botonPuntos.setOnClickListener(
                vista -> abrirPuntosRecoleccion()
        );
    }

    private void abrirClasificacion() {

        Intent intent =
                new Intent(
                        MainActivity.this,
                        ClasificarResiduoActivity.class
                );

        startActivity(intent);
    }

    private void abrirPuntosRecoleccion() {

        Intent intent =
                new Intent(
                        MainActivity.this,
                        PuntosRecoleccionActivity.class
                );

        startActivity(intent);
    }
}