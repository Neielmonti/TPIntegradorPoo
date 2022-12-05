package programa.controlador;
import java.io.Serializable;
public enum Evento implements Serializable {
    JUGADA_ARMADA,
    CAMBIO_DE_JUGADOR,
    MANO_ACTUALIZADA,
    POZO_ACTUALIZADO,
    JUGADA_RECHAZADA,
    JUGADOR_BAJO,
    BAJADA_RECHAZADA,
    JUGADA_MODIFICADA,
    DESCARGA_RECHAZADA,
    RONDA_GANADA,
    JUGADOR_AGREGADO,
}