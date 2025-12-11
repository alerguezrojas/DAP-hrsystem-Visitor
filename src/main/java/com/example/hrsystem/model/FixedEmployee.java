package com.example.hrsystem.model;

import com.example.hrsystem.visitor.Visitor;
import com.example.hrsystem.visitor.WorkforceItem;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FixedEmployee implements WorkforceItem {
    private String name;
    private double monthlySalary;
    private double bonus;

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}