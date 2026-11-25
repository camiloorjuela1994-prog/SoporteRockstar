package com.example.soporterockstar.soportetecnico.repositorioapp;

import com.example.soporterockstar.soportetecnico.modeloapp.OrdendelServicio;
import java.util.ArrayList;
import java.util.List;

/**
 * Empresa: SoporteRockstar
 * Descripción: Repositorio en memoria que gestiona el CRUD básico y métricas acumuladas.
 */
public class OrdenLista {
    private final ArrayList<OrdendelServicio> listaOrdenes;

    public OrdenLista() {
        this.listaOrdenes = new ArrayList<>();
    }

    // CREATE: Registrar nueva orden
    public void agregarOrden(OrdendelServicio orden) {
        if (orden != null) {
            listaOrdenes.add(orden);
        }
    }

    // READ: Obtener todas las órdenes
    public List<OrdendelServicio> obtenerTodas() {
        return new ArrayList<>(listaOrdenes);
    }

    // Métricas del negocio
    public int getCantidadOrdenes() {
        return listaOrdenes.size();
    }

    public double calcularTotalRecaudado() {
        double total = 0;
        for (OrdendelServicio orden : listaOrdenes) {
            total += orden.calcularTotal(); // Polimorfismo en tiempo de ejecución
        }
        return total;
    }

    public double calcularPromedioVentas() {
        if (listaOrdenes.isEmpty()) return 0.0;
        return calcularTotalRecaudado() / listaOrdenes.size();
    }
}