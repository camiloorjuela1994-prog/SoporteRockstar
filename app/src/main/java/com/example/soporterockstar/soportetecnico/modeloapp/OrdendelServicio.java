package com.example.soporterockstar.soportetecnico.modeloapp;

/**
 * Empresa: SoporteRockstar
 * Autores: [Nombres de los integrantes]
 * Descripción: Superclase abstracta que representa una orden base de Soporte Técnico.
 * Implementa encapsulamiento, validaciones de negocio y contratos polimórficos.
 */
public abstract class OrdendelServicio {
    private String nombreCliente;
    private String telefonoCliente;
    private String modeloDispositivo;
    private double costoManoObra;
    private boolean servicioUrgente; // Recargo adicional acumulativo

    public OrdendelServicio(String nombreCliente, String telefonoCliente, String modeloDispositivo, double costoManoObra, boolean servicioUrgente) {
        setNombreCliente(nombreCliente);
        setTelefonoCliente(telefonoCliente);
        setModeloDispositivo(modeloDispositivo);
        setCostoManoObra(costoManoObra);
        this.servicioUrgente = servicioUrgente;
    }

    // --- Getters y Setters con Validación ---
    public String getNombreCliente() { return nombreCliente; }

    public void setNombreCliente(String nombreCliente) {
        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        this.nombreCliente = nombreCliente.trim();
    }

    public String getTelefonoCliente() { return telefonoCliente; }

    public void setTelefonoCliente(String telefonoCliente) {
        if (telefonoCliente == null || telefonoCliente.trim().length() < 7) {
            throw new IllegalArgumentException("Ingrese un teléfono válido (mínimo 7 dígitos).");
        }
        this.telefonoCliente = telefonoCliente.trim();
    }

    public String getModeloDispositivo() { return modeloDispositivo; }

    public void setModeloDispositivo(String modeloDispositivo) {
        if (modeloDispositivo == null || modeloDispositivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe especificar el modelo del dispositivo.");
        }
        this.modeloDispositivo = modeloDispositivo.trim();
    }

    public double getCostoManoObra() { return costoManoObra; }

    public void setCostoManoObra(double costoManoObra) {
        if (costoManoObra <= 0) {
            throw new IllegalArgumentException("El costo de mano de obra debe ser mayor a cero.");
        }
        this.costoManoObra = costoManoObra;
    }

    public boolean isServicioUrgente() { return servicioUrgente; }
    public void setServicioUrgente(boolean servicioUrgente) { this.servicioUrgente = servicioUrgente; }

    // Métodos abstractos que cada subclase especializará (@Override)
    public abstract double calcularTotal();
    public abstract String getTipoServicio();

    @Override
    public String toString() {
        return "[" + getTipoServicio() + "] " + modeloDispositivo +
                "\nCliente: " + nombreCliente + " (" + telefonoCliente + ")" +
                "\nUrgente: " + (servicioUrgente ? "Sí (+20%)" : "No") +
                "\nTotal a pagar: $" + String.format("%.2f", calcularTotal());
    }
}