package com.example.hrsystem.visitor;

import com.example.hrsystem.model.*;
import org.springframework.stereotype.Component;

@Component
public class PayrollVisitor implements Visitor<Double> {
    @Override
    public Double visit(FixedEmployee emp) {
        return (emp.getMonthlySalary() + emp.getBonus()) * 1.30;
    }
    @Override
    public Double visit(Freelancer free) {
        return (double) (free.getHoursWorked() * free.getHourlyRate());
    }
    @Override
    public Double visit(Intern intern) {
        return intern.getAidAmount() + 50.0;
    }
}