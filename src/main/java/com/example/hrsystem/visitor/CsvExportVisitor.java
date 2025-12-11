package com.example.hrsystem.visitor;

import com.example.hrsystem.model.*;
import org.springframework.stereotype.Component;

@Component
public class CsvExportVisitor implements Visitor<String> {

    // Helper para formatear números como "3000,50" en vez de "3000.50"
    private String format(double number) {
        return String.format("%.2f", number);
    }

    @Override
    public String visit(FixedEmployee emp) {
        // FIJO;Nombre;Salario
        return "FIJO;" + emp.getName() + ";" + format(emp.getMonthlySalary());
    }

    @Override
    public String visit(Freelancer free) {
        // FREELANCE;Nombre;PrecioHora
        return "FREELANCE;" + free.getName() + ";" + format(free.getHourlyRate());
    }

    @Override
    public String visit(Intern intern) {
        // BECARIO;Nombre;Ayuda
        return "BECARIO;" + intern.getName() + ";" + format(intern.getAidAmount());
    }
}