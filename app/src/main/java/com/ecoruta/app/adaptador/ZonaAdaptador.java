package com.ecoruta.app.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ecoruta.app.R;
import com.ecoruta.app.dto.ZonaRespuestaDTO;

import java.util.Objects;

public final class ZonaAdaptador
        extends ListAdapter<
        ZonaRespuestaDTO,
        ZonaAdaptador.ZonaViewHolder> {

    private static final DiffUtil.ItemCallback<ZonaRespuestaDTO>
            COMPARADOR_ZONAS =
            new DiffUtil.ItemCallback<>() {

                @Override
                public boolean areItemsTheSame(
                        @NonNull ZonaRespuestaDTO anterior,
                        @NonNull ZonaRespuestaDTO nueva) {

                    return Objects.equals(
                            anterior.getIdZona(),
                            nueva.getIdZona()
                    );
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull ZonaRespuestaDTO anterior,
                        @NonNull ZonaRespuestaDTO nueva) {

                    return Objects.equals(
                            anterior.getNombreZona(),
                            nueva.getNombreZona()
                    )
                            && Objects.equals(
                            anterior.getTipoZona(),
                            nueva.getTipoZona()
                    )
                            && Objects.equals(
                            anterior.getSector(),
                            nueva.getSector()
                    )
                            && Objects.equals(
                            anterior.getMunicipio(),
                            nueva.getMunicipio()
                    )
                            && Objects.equals(
                            anterior.getHorario(),
                            nueva.getHorario()
                    );
                }
            };

    public ZonaAdaptador() {
        super(COMPARADOR_ZONAS);
    }

    @NonNull
    @Override
    public ZonaViewHolder onCreateViewHolder(
            @NonNull ViewGroup padre,
            int tipoVista) {

        View vista =
                LayoutInflater
                        .from(padre.getContext())
                        .inflate(
                                R.layout.item_zona,
                                padre,
                                false
                        );

        return new ZonaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ZonaViewHolder holder,
            int posicion) {

        holder.vincular(
                getItem(posicion)
        );
    }

    static final class ZonaViewHolder
            extends RecyclerView.ViewHolder {

        private final TextView textoNombre;
        private final TextView textoTipo;
        private final TextView textoSector;
        private final TextView textoMunicipio;
        private final TextView textoHorario;

        ZonaViewHolder(
                @NonNull View itemView) {

            super(itemView);

            textoNombre =
                    itemView.findViewById(
                            R.id.textoNombreZona
                    );

            textoTipo =
                    itemView.findViewById(
                            R.id.textoTipoZona
                    );

            textoSector =
                    itemView.findViewById(
                            R.id.textoSectorZona
                    );

            textoMunicipio =
                    itemView.findViewById(
                            R.id.textoMunicipioZona
                    );

            textoHorario =
                    itemView.findViewById(
                            R.id.textoHorarioZona
                    );
        }

        void vincular(
                ZonaRespuestaDTO zona) {

            textoNombre.setText(
                    obtenerValorSeguro(
                            zona.getNombreZona()
                    )
            );

            textoTipo.setText(
                    itemView.getContext().getString(
                            R.string.zona_tipo_formato,
                            obtenerValorSeguro(
                                    zona.getTipoZona()
                            )
                    )
            );

            textoSector.setText(
                    itemView.getContext().getString(
                            R.string.zona_sector_formato,
                            obtenerValorSeguro(
                                    zona.getSector()
                            )
                    )
            );

            textoMunicipio.setText(
                    itemView.getContext().getString(
                            R.string.zona_municipio_formato,
                            obtenerValorSeguro(
                                    zona.getMunicipio()
                            )
                    )
            );

            textoHorario.setText(
                    itemView.getContext().getString(
                            R.string.zona_horario_formato,
                            obtenerValorSeguro(
                                    zona.getHorario()
                            )
                    )
            );
        }

        private String obtenerValorSeguro(
                String valor) {

            if (valor == null
                    || valor.isBlank()) {

                return itemView
                        .getContext()
                        .getString(
                                R.string.dato_no_disponible
                        );
            }

            return valor.trim();
        }
    }
}