package com.example.soporterockstar.soportetecnico.modeloapp;

/**
 * Empresa: SoporteRockstar
 * Descripción: Subclase especializada en reparaciones físicas (Hardware).
 * Añade costo de repuestos e impuestos (19% IVA) sobre piezas.
 */
public class OrdenHardware extends OrdendelServicio {
    private double costoRepuestos;

    public OrdenHardware(String nombreCliente, String telefonoCliente, String modeloDispositivo,
                         double costoManoObra, boolean servicioUrgente, double costoRepuestos) {
        super(nombreCliente, telefonoCliente, modeloDispositivo, costoManoObra, servicioUrgente);
        setCostoRepuestos(costoRepuestos);
    }

    public double getCostoRepuestos() { return costoRepuestos; }

    public void setCostoRepuestos(double costoRepuestos) {
        if (costoRepuestos < 0) {
            throw new IllegalArgumentException("El costo de repuestos no puede ser negativo.");
        }
        this.costoRepuestos = costoRepuestos;
    }

    @Override
    public double calcularTotal() {
        double subtotal = getCostoManoObra() + (costoRepuestos * 1.19);
        if (isServicioUrgente()) {
            subtotal *= 1.20; // Recargo del 20% por atención prioritaria
        }
        return subtotal;
    }

    @Override
    public String getTipoServicio() {
        return "HARDWARE";
    }
}