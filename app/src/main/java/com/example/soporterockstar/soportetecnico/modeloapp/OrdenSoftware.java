package com.example.soporterockstar.soportetecnico.modeloapp;

/**
 * Empresa: SoporteRockstar
 * Descripción: Subclase especializada en mantenimiento lógico (Software).
 * Incluye tarifa fija de licencias/respaldo y descuento del 10% si no es urgente.
 */
public class OrdenSoftware extends OrdendelServicio {
    private static final double TARIFA_LICENCIAS = 15000.0;

    public OrdenSoftware(String nombreCliente, String telefonoCliente, String modeloDispositivo,
                         double costoManoObra, boolean servicioUrgente) {
        super(nombreCliente, telefonoCliente, modeloDispositivo, costoManoObra, servicioUrgente);
    }

    @Override
    public double calcularTotal() {
        double base = getCostoManoObra() + TARIFA_LICENCIAS;
        if (isServicioUrgente()) {
            return base * 1.20; // Recargo del 20% por urgencia
        } else {
            return base * 0.90; // 10% de descuento en mantenimiento estándar
        }
    }

    @Override
    public String getTipoServicio() {
        return "SOFTWARE";
    }
}