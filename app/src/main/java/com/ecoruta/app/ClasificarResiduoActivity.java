package com.ecoruta.app;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ClasificarResiduoActivity
        extends AppCompatActivity {

    private static final Set<String> APROVECHABLES =
            crearConjunto(
                    "botella plastica",
                    "botella de plastico",
                    "plastico",
                    "papel",
                    "carton",
                    "vidrio",
                    "lata"
            );

    private static final Set<String> ORGANICOS =
            crearConjunto(
                    "cascara de banano",
                    "cascara",
                    "restos de comida",
                    "organico",
                    "hojas"
            );

    private static final Set<String> NO_APROVECHABLES =
            crearConjunto(
                    "servilleta",
                    "papel higienico",
                    "tapabocas",
                    "panal",
                    "icopor sucio",
                    "colilla",
                    "ceramica",
                    "espejo roto"
            );

    private static final Set<String> PELIGROSOS =
            crearConjunto(
                    "pila",
                    "pilas",
                    "bateria",
                    "medicamentos",
                    "quimicos",
                    "aerosol",
                    "aceite usado",
                    "jeringa",
                    "pintura"
            );

    private TextInputEditText campoResiduo;
    private TextView textoCategoria;
    private TextView textoOrientacion;
    private MaterialCardView tarjetaResultado;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(
                R.layout.activity_clasificar_residuo
        );

        configurarBordesPantalla();
        inicializarComponentes();
        configurarEventos();
    }

    private static Set<String> crearConjunto(
            String... elementos) {

        return Collections.unmodifiableSet(
                new HashSet<>(
                        Arrays.asList(elementos)
                )
        );
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

    private void inicializarComponentes() {

        campoResiduo =
                findViewById(
                        R.id.campoResiduo
                );

        textoCategoria =
                findViewById(
                        R.id.textoCategoria
                );

        textoOrientacion =
                findViewById(
                        R.id.textoOrientacion
                );

        tarjetaResultado =
                findViewById(
                        R.id.tarjetaResultado
                );
    }

    private void configurarEventos() {

        MaterialButton botonClasificar =
                findViewById(
                        R.id.btnEjecutarClasificacion
                );

        MaterialButton botonVolver =
                findViewById(
                        R.id.btnVolver
                );

        botonClasificar.setOnClickListener(
                vista -> clasificarResiduo()
        );

        botonVolver.setOnClickListener(
                vista -> finish()
        );

        campoResiduo.setOnEditorActionListener(
                (vista, accion, evento) -> {

                    if (accion
                            == EditorInfo.IME_ACTION_DONE) {

                        clasificarResiduo();
                        return true;
                    }

                    return false;
                }
        );
    }

    private void clasificarResiduo() {

        String residuoIngresado =
                obtenerTextoCampo();

        campoResiduo.setError(null);

        if (residuoIngresado.isBlank()) {

            campoResiduo.setError(
                    getString(
                            R.string.error_residuo_obligatorio
                    )
            );

            campoResiduo.requestFocus();

            mostrarResultado(
                    R.string.resultado_pendiente,
                    R.string.error_residuo_obligatorio,
                    R.color.eco_resultado_amarillo
            );

            return;
        }

        String residuoNormalizado =
                normalizarTexto(
                        residuoIngresado
                );

        if (APROVECHABLES.contains(
                residuoNormalizado)) {

            mostrarResultado(
                    R.string.categoria_aprovechable,
                    R.string.orientacion_aprovechable,
                    R.color.eco_resultado_verde
            );

            return;
        }

        if (ORGANICOS.contains(
                residuoNormalizado)) {

            mostrarResultado(
                    R.string.categoria_organico,
                    R.string.orientacion_organico,
                    R.color.eco_resultado_verde
            );

            return;
        }

        if (NO_APROVECHABLES.contains(
                residuoNormalizado)) {

            mostrarResultado(
                    R.string.categoria_no_aprovechable,
                    R.string.orientacion_no_aprovechable,
                    R.color.eco_resultado_gris
            );

            return;
        }

        if (PELIGROSOS.contains(
                residuoNormalizado)) {

            mostrarResultado(
                    R.string.categoria_peligroso,
                    R.string.orientacion_peligroso,
                    R.color.eco_resultado_rojo
            );

            return;
        }

        mostrarResultado(
                R.string.categoria_desconocida,
                R.string.orientacion_desconocida,
                R.color.eco_resultado_amarillo
        );
    }

    private String obtenerTextoCampo() {

        if (campoResiduo.getText() == null) {
            return "";
        }

        return campoResiduo
                .getText()
                .toString()
                .trim();
    }

    private String normalizarTexto(
            String texto) {

        String textoSinTildes =
                Normalizer.normalize(
                                texto,
                                Normalizer.Form.NFD
                        )
                        .replaceAll(
                                "\\p{M}+",
                                ""
                        );

        return textoSinTildes
                .toLowerCase(Locale.ROOT)
                .trim();
    }

    private void mostrarResultado(
            int recursoCategoria,
            int recursoOrientacion,
            int recursoColor) {

        textoCategoria.setText(
                recursoCategoria
        );

        textoOrientacion.setText(
                recursoOrientacion
        );

        tarjetaResultado.setCardBackgroundColor(
                ContextCompat.getColor(
                        this,
                        recursoColor
                )
        );
    }
}