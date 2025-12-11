package com.example.hrsystem.visitor;

import com.example.hrsystem.model.*;
import org.springframework.stereotype.Component;

@Component
public class SalaryIncreaseVisitor implements Visitor<Void> {

    private double percentage;

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public Void visit(FixedEmployee emp) {
        double old = emp.getMonthlySalary();
        emp.setMonthlySalary(old * (1 + percentage / 100));
        return null;
    }

    @Override
    public Void visit(Freelancer free) {
        double old = free.getHourlyRate();
        free.setHourlyRate(old * (1 + percentage / 100));
        return null;
    }

    @Override
    public Void visit(Intern intern) {
        return null;
    }
}