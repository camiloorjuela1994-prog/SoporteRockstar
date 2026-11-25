package com.example.soporterockstar.soportetecnico;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soporterockstar.soportetecnico.modeloapp.OrdenHardware;
import com.example.soporterockstar.soportetecnico.modeloapp.OrdenSoftware;
import com.example.soporterockstar.soportetecnico.modeloapp.OrdendelServicio;
import com.example.soporterockstar.soportetecnico.repositorioapp.OrdenLista;

/**
 * Empresa: SoporteRockstar
 * Descripción: Activity principal de Soporte Técnico que conecta la interfaz XML con el repositorio en memoria.
 */
public class MainActivity extends AppCompatActivity {

    private EditText etNombreCliente, etTelefono, etDispositivo, etManoObra, etRepuestos;
    private RadioGroup rgTipoServicio;
    private RadioButton rbHardware, rbSoftware;
    private CheckBox cbUrgente;
    private Button btnRegistrar, btnLimpiar;
    private TextView tvResumenConsolidado;

    private OrdenLista repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repositorio = new OrdenLista();
        enlazarVistas();
        configurarEventos();
    }

    private void enlazarVistas() {
        etNombreCliente = findViewById(R.id.etNombreCliente);
        etTelefono = findViewById(R.id.etTelefono);
        etDispositivo = findViewById(R.id.etDispositivo);
        etManoObra = findViewById(R.id.etManoObra);
        etRepuestos = findViewById(R.id.etRepuestos);
        rgTipoServicio = findViewById(R.id.rgTipoServicio);
        rbHardware = findViewById(R.id.rbHardware);
        rbSoftware = findViewById(R.id.rbSoftware);
        cbUrgente = findViewById(R.id.cbUrgente);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        tvResumenConsolidado = findViewById(R.id.tvResumenConsolidado);
    }

    private void configurarEventos() {
        rgTipoServicio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbSoftware) {
                etRepuestos.setEnabled(false);
                etRepuestos.setText("0");
            } else {
                etRepuestos.setEnabled(true);
                etRepuestos.setText("");
            }
        });

        btnRegistrar.setOnClickListener(v -> procesarRegistro());
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
    }

    private void procesarRegistro() {
        String cliente = etNombreCliente.getText().toString();
        String telefono = etTelefono.getText().toString();
        String dispositivo = etDispositivo.getText().toString();
        String strManoObra = etManoObra.getText().toString();
        String strRepuestos = etRepuestos.getText().toString();
        boolean esUrgente = cbUrgente.isChecked();

        if (cliente.trim().isEmpty() || telefono.trim().isEmpty() ||
                dispositivo.trim().isEmpty() || strManoObra.trim().isEmpty()) {
            Toast.makeText(this, "Complete todos los campos obligatorios.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rbHardware.isChecked() && strRepuestos.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el valor de los repuestos (0 si no aplica).", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double manoObra = Double.parseDouble(strManoObra);
            OrdendelServicio nuevaOrden;

            if (rbHardware.isChecked()) {
                double repuestos = Double.parseDouble(strRepuestos);
                nuevaOrden = new OrdenHardware(cliente, telefono, dispositivo, manoObra, esUrgente, repuestos);
            } else {
                nuevaOrden = new OrdenSoftware(cliente, telefono, dispositivo, manoObra, esUrgente);
            }

            repositorio.agregarOrden(nuevaOrden);
            actualizarVistaConsolidada(nuevaOrden);
            Toast.makeText(this, "Orden de Soporte Técnico registrada.", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato numérico inválido en los costos.", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void actualizarVistaConsolidada(OrdendelServicio ultimaOrden) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ÚLTIMA ORDEN SOPORTE TÉCNICO ===\n");
        sb.append(ultimaOrden.toString()).append("\n\n");

        sb.append("=== MÉTRICAS SOPORTEROCKSTAR ===\n");
        sb.append("Total Órdenes: ").append(repositorio.getCantidadOrdenes()).append("\n");
        sb.append("Total Recaudado: $").append(String.format("%.2f", repositorio.calcularTotalRecaudado())).append("\n");
        sb.append("Ticket Promedio: $").append(String.format("%.2f", repositorio.calcularPromedioVentas())).append("\n\n");

        sb.append("=== HISTORIAL EN MEMORIA ===\n");
        int contador = 1;
        for (OrdendelServicio orden : repositorio.obtenerTodas()) {
            sb.append(contador++).append(". ")
                    .append(orden.getModeloDispositivo())
                    .append(" [").append(orden.getTipoServicio()).append("] - $")
                    .append(String.format("%.2f", orden.calcularTotal())).append("\n");
        }

        tvResumenConsolidado.setText(sb.toString());
    }

    private void limpiarFormulario() {
        etNombreCliente.setText("");
        etTelefono.setText("");
        etDispositivo.setText("");
        etManoObra.setText("");
        rbHardware.setChecked(true);
        etRepuestos.setEnabled(true);
        etRepuestos.setText("");
        cbUrgente.setChecked(false);
        etNombreCliente.requestFocus();
        Toast.makeText(this, "Formulario listo para nueva orden.", Toast.LENGTH_SHORT).show();
    }
}