package com.example.hrsystem.visitor;

import com.example.hrsystem.model.*;
import org.springframework.stereotype.Component;

@Component
public class HtmlDashboardVisitor implements Visitor<String> {
    @Override
    public String visit(FixedEmployee emp) {
        return String.format(
                "<div style='border: 2px solid #2980b9; padding: 10px; margin: 10px; border-radius: 8px; width: 200px; background-color: #eaf2f8;'>" +
                        "<h3 style='color:#2980b9'>👨‍💼 %s</h3><p>Fijo</p><p>Base: %.2f €</p></div>",
                emp.getName(), emp.getMonthlySalary());
    }
    @Override
    public String visit(Freelancer free) {
        return String.format(
                "<div style='border: 2px solid #e67e22; padding: 10px; margin: 10px; border-radius: 8px; width: 200px; background-color: #fdf2e9;'>" +
                        "<h3 style='color:#e67e22'>💻 %s</h3><p>Freelance</p><p>Tarifa: %.2f €/h</p></div>",
                free.getName(), free.getHourlyRate());
    }
    @Override
    public String visit(Intern intern) {
        return String.format(
                "<div style='border: 2px solid #27ae60; padding: 10px; margin: 10px; border-radius: 8px; width: 200px; background-color: #e9f7ef;'>" +
                        "<h3 style='color:#27ae60'>🎓 %s</h3><p>Becario</p><p>Ayuda: %.2f €</p></div>",
                intern.getName(), intern.getAidAmount());
    }
}