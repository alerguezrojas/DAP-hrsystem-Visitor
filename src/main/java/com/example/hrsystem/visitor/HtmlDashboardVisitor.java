package com.example.hrsystem.visitor;

import com.example.hrsystem.model.*;
import org.springframework.stereotype.Component;

@Component
public class HtmlDashboardVisitor implements Visitor<String> {

    // Helper para formatear dinero
    private String format(double number) {
        return String.format("%,.2f €", number);
    }

    @Override
    public String visit(FixedEmployee emp) {
        // Lógica de cálculo visual (debe coincidir con PayrollVisitor para ser coherente)
        double base = emp.getMonthlySalary();
        double bonus = emp.getBonus();
        double subtotal = base + bonus;
        double impuestos = subtotal * 0.30; // El 30% extra
        double total = subtotal + impuestos;

        return String.format(
                "<div style='border-top: 4px solid #3498db; box-shadow: 0 2px 5px rgba(0,0,0,0.1); padding: 15px; margin: 10px; border-radius: 8px; width: 260px; background-color: white; font-family: Arial;'>" +
                        "<h3 style='color:#2c3e50; margin-top:0;'>👨‍💼 %s</h3>" +
                        "<p style='color:#7f8c8d; font-size: 12px; font-weight:bold; text-transform:uppercase;'>Empleado Fijo</p>" +

                        // Tabla de desglose
                        "<table style='width:100%%; font-size: 13px; border-collapse: collapse;'>" +
                        "<tr><td>Salario Base:</td><td style='text-align:right;'>%s</td></tr>" +
                        "<tr><td>Bonus:</td><td style='text-align:right;'>%s</td></tr>" +
                        "<tr style='color:#c0392b;'><td>Seg. Social (+30%%):</td><td style='text-align:right;'>%s</td></tr>" +
                        "<tr style='font-weight:bold; border-top:1px solid #eee;'><td>Coste Total:</td><td style='text-align:right;'>%s</td></tr>" +
                        "</table>" +
                        "</div>",
                emp.getName(), format(base), format(bonus), format(impuestos), format(total));
    }

    @Override
    public String visit(Freelancer free) {
        // Cálculo visual
        double total = free.getHoursWorked() * free.getHourlyRate();

        return String.format(
                "<div style='border-top: 4px solid #e67e22; box-shadow: 0 2px 5px rgba(0,0,0,0.1); padding: 15px; margin: 10px; border-radius: 8px; width: 260px; background-color: white; font-family: Arial;'>" +
                        "<h3 style='color:#2c3e50; margin-top:0;'>💻 %s</h3>" +
                        "<p style='color:#7f8c8d; font-size: 12px; font-weight:bold; text-transform:uppercase;'>Freelance</p>" +

                        "<table style='width:100%%; font-size: 13px; border-collapse: collapse;'>" +
                        "<tr><td>Horas:</td><td style='text-align:right;'>%d h</td></tr>" +
                        "<tr><td>Tarifa:</td><td style='text-align:right;'>%s /h</td></tr>" +
                        "<tr style='font-weight:bold; border-top:1px solid #eee;'><td>Factura Total:</td><td style='text-align:right;'>%s</td></tr>" +
                        "</table>" +
                        "</div>",
                free.getName(), free.getHoursWorked(), format(free.getHourlyRate()), format(total));
    }

    @Override
    public String visit(Intern intern) {
        // Cálculo visual (Ayuda + 50 gastos gestión)
        double costoGestion = 50.0;
        double total = intern.getAidAmount() + costoGestion;

        return String.format(
                "<div style='border-top: 4px solid #27ae60; box-shadow: 0 2px 5px rgba(0,0,0,0.1); padding: 15px; margin: 10px; border-radius: 8px; width: 260px; background-color: white; font-family: Arial;'>" +
                        "<h3 style='color:#2c3e50; margin-top:0;'>🎓 %s</h3>" +
                        "<p style='color:#7f8c8d; font-size: 12px; font-weight:bold; text-transform:uppercase;'>Becario</p>" +

                        "<table style='width:100%%; font-size: 13px; border-collapse: collapse;'>" +
                        "<tr><td>Ayuda Base:</td><td style='text-align:right;'>%s</td></tr>" +
                        "<tr><td>Coste Gestión:</td><td style='text-align:right;'>%s</td></tr>" +
                        "<tr style='font-weight:bold; border-top:1px solid #eee;'><td>Coste Total:</td><td style='text-align:right;'>%s</td></tr>" +
                        "</table>" +
                        "</div>",
                intern.getName(), format(intern.getAidAmount()), format(costoGestion), format(total));
    }
}