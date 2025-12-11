package com.example.hrsystem.controller;

import com.example.hrsystem.model.*;
import com.example.hrsystem.visitor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HrController {

    private final HtmlDashboardVisitor htmlVisitor;
    private final PayrollVisitor payrollVisitor;
    private final SalaryIncreaseVisitor salaryIncreaseVisitor;
    private final CsvExportVisitor csvExportVisitor;

    private List<WorkforceItem> database = new ArrayList<>();

    @Autowired
    public HrController(HtmlDashboardVisitor htmlVisitor,
                        PayrollVisitor payrollVisitor,
                        SalaryIncreaseVisitor salaryIncreaseVisitor,
                        CsvExportVisitor csvExportVisitor) {
        this.htmlVisitor = htmlVisitor;
        this.payrollVisitor = payrollVisitor;
        this.salaryIncreaseVisitor = salaryIncreaseVisitor;
        this.csvExportVisitor = csvExportVisitor;

        // Datos iniciales
        database.add(new FixedEmployee("Ana Directora", 5000.0, 1000.0));
        database.add(new FixedEmployee("Carlos IT", 3000.0, 200.0));
        database.add(new Freelancer("Laura Web", 100, 45.0));
        database.add(new Intern("Pepe Junior", 600.0));
    }

    // --- DASHBOARD VISUAL (Restaurado) ---
    @GetMapping("/dashboard")
    public String getDashboard() {
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family: Arial; padding: 20px; background-color: #f4f6f7;'>");

        // 1. Panel de Control (Estilo Oscuro)
        html.append("<div style='background: #2c3e50; color: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);'>");
        html.append("<h2 style='margin-top:0;'>Panel de Control RRHH</h2>");

        // Botón: Subir Sueldo (Verde)
        html.append("<form action='/api/acciones/subir' method='get' style='display:inline; margin-right:15px;'>");
        html.append("<input type='hidden' name='pct' value='10'>");
        html.append("<button style='padding: 12px 20px; background: #27ae60; color: white; border: none; cursor: pointer; font-size: 16px; border-radius: 4px; font-weight: bold;'>💰 Aplicar Subida 10%</button>");
        html.append("</form>");

        // Botón: Descargar CSV (Azul)
        html.append("<a href='/api/acciones/descargar' download='empleados.csv' style='text-decoration:none;'>");
        html.append("<button style='padding: 12px 20px; background: #2980b9; color: white; border: none; cursor: pointer; font-size: 16px; border-radius: 4px; font-weight: bold;'>📥 Descargar Excel (CSV)</button>");
        html.append("</a>");
        html.append("</div>");

        // 2. Lista de Tarjetas
        html.append("<div style='display: flex; flex-wrap: wrap; gap: 15px;'>");

        double totalCost = 0;

        for (WorkforceItem item : database) {
            // A. Visitor Visual: Genera la tarjeta HTML
            html.append(item.accept(htmlVisitor));

            // B. Visitor Financiero: Calcula el coste para el total de abajo
            totalCost += item.accept(payrollVisitor);
        }
        html.append("</div>");

        // 3. Pie de página con Total (Restaurado)
        html.append("<div style='margin-top: 30px; padding: 20px; background: white; border-radius: 8px; border-left: 5px solid #2c3e50;'>");
        html.append("<h3 style='margin:0; color: #2c3e50;'>Coste Total Mensual Estimado: " + String.format("%,.2f", totalCost) + " €</h3>");
        html.append("</div>");

        html.append("</body></html>");

        return html.toString();
    }

    // --- ACCIÓN: SUBIR SUELDO ---
    @GetMapping("/acciones/subir")
    public String applyRaise(@RequestParam double pct) {
        salaryIncreaseVisitor.setPercentage(pct);
        for (WorkforceItem item : database) {
            item.accept(salaryIncreaseVisitor);
        }
        return "<script>alert('Sueldos actualizados correctamente'); window.location.href='/api/dashboard';</script>";
    }

    // --- ACCIÓN: DESCARGAR CSV (Formato Excel España) ---
    @GetMapping("/acciones/descargar")
    public ResponseEntity<String> downloadCsv() {
        StringBuilder csv = new StringBuilder();

        // Cabecera con ;
        csv.append("TIPO;NOMBRE;COSTE_NOMINA_ESTIMADO\n");

        double costeTotalEmpresa = 0;

        for (WorkforceItem item : database) {
            // Línea del empleado (usa formato español gracias a CsvExportVisitor)
            csv.append(item.accept(csvExportVisitor)).append("\n");

            // Sumamos al total
            costeTotalEmpresa += item.accept(payrollVisitor);
        }

        // Fila Total con ; y formato de número con coma
        csv.append("TOTAL;;")
                .append(String.format("%.2f", costeTotalEmpresa));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"staff_report.csv\"")
                .body(csv.toString());
    }
}