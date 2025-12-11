package com.example.hrsystem.visitor;

import com.example.hrsystem.model.FixedEmployee;
import com.example.hrsystem.model.Freelancer;
import com.example.hrsystem.model.Intern;
import org.springframework.stereotype.Component;

@Component
public class PayrollVisitor implements Visitor<Double> {

    @Override
    public Double visit(FixedEmployee emp) {
        // Salario + Bonus + 30% costes empresa
        return (emp.getMonthlySalary() + emp.getBonus()) * 1.30;
    }

    @Override
    public Double visit(Freelancer free) {
        // El freelancer cobra tal cual, sin costes sociales para la empresa contratante
        return (double) (free.getHoursWorked() * free.getHourlyRate());
    }

    @Override
    public Double visit(Intern intern) {
        // La beca tiene un coste fijo administrativo de +50€
        return intern.getAidAmount() + 50.0;
    }
}