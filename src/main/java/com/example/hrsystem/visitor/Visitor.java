package com.example.hrsystem.visitor;

import com.example.hrsystem.model.FixedEmployee;
import com.example.hrsystem.model.Freelancer;
import com.example.hrsystem.model.Intern;

public interface Visitor<R> {
    R visit(FixedEmployee employee);
    R visit(Freelancer freelancer);
    R visit(Intern intern);
}