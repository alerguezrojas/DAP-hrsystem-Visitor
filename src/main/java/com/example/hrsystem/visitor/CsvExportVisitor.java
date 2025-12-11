package com.example.hrsystem.visitor;

import com.example.hrsystem.model.*;
import org.springframework.stereotype.Component;

@Component
public class CsvExportVisitor implements Visitor<String> {

    // Formateador para Excel España (coma decimal)
    private String format(double number) {
        return String.format("%.2f", number);
    }

    @Override
    public String visit(FixedEmployee emp) {
        // Cálculo del desglose
        double base = emp.getMonthlySalary();
        double bonus = emp.getBonus();
        double impuestos = (base + bonus) * 0.30;
        double total = base + bonus + impuestos;

        // Estructura: TIPO; NOMBRE; BASE; EXTRAS; IMPUESTOS; TOTAL
        return "FIJO;" + emp.getName() + ";" +
                format(base) + ";" +
                format(bonus) + ";" +
                format(impuestos) + ";" +
                format(total);
    }

    @Override
    public String visit(Freelancer free) {
        // El freelance es todo base (horas * tarifa)
        double total = free.getHoursWorked() * free.getHourlyRate();

        // En EXTRAS ponemos 0, en IMPUESTOS ponemos 0 (para la empresa)
        return "FREELANCE;" + free.getName() + ";" +
                format(total) + ";" +
                "0,00" + ";" +
                "0,00" + ";" +
                format(total);
    }

    @Override
    public String visit(Intern intern) {
        double base = intern.getAidAmount();
        double gastosGestion = 50.0;
        double total = base + gastosGestion;

        // Ponemos los 50€ en la columna de Impuestos/Gastos
        return "BECARIO;" + intern.getName() + ";" +
                format(base) + ";" +
                "0,00" + ";" +
                format(gastosGestion) + ";" +
                format(total);
    }
}