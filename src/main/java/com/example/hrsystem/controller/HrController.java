package com.example.hrsystem.controller;

import com.example.hrsystem.model.FixedEmployee;
import com.example.hrsystem.model.Freelancer;
import com.example.hrsystem.model.Intern;
import com.example.hrsystem.visitor.HtmlDashboardVisitor;
import com.example.hrsystem.visitor.PayrollVisitor;
import com.example.hrsystem.visitor.WorkforceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HrController {

    private final PayrollVisitor payrollVisitor;
    private final HtmlDashboardVisitor htmlVisitor;

    // Inyección de dependencias por constructor
    @Autowired
    public HrController(PayrollVisitor payrollVisitor, HtmlDashboardVisitor htmlVisitor) {
        this.payrollVisitor = payrollVisitor;
        this.htmlVisitor = htmlVisitor;
    }

    // Simulamos una base de datos en memoria
    private List<WorkforceItem> getDatabaseData() {
        return List.of(
                new FixedEmployee("Ana Gerente", 4500.00, 1500.00),
                new FixedEmployee("Luis Sistemas", 3200.00, 200.00),
                new Freelancer("Mark Frontend", 80, 55.0),
                new Freelancer("Sarah Diseño", 40, 60.0),
                new Intern("Javi Becario", 600.00)
        );
    }

    // Endpoint 1: Calcula costes (Usa PayrollVisitor)
    @GetMapping("/nominas")
    public String getTotalesNomina() {
        List<WorkforceItem> staff = getDatabaseData();
        double totalCost = 0;

        for (WorkforceItem person : staff) {
            // Aquí ocurre la magia del Visitor
            totalCost += person.accept(payrollVisitor);
        }

        return "<h1>Coste Total de Nóminas</h1>" +
                "<p>El coste total para la empresa este mes es: <b>" + totalCost + " €</b></p>";
    }

    // Endpoint 2: Genera Dashboard (Usa HtmlDashboardVisitor)
    @GetMapping("/dashboard")
    public String getDashboard() {
        List<WorkforceItem> staff = getDatabaseData();
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<html><body style='font-family: Arial;'>");
        htmlBuilder.append("<h1>Panel de Recursos Humanos</h1>");
        htmlBuilder.append("<div style='display: flex; flex-wrap: wrap;'>");

        for (WorkforceItem person : staff) {
            // Aquí reutilizamos los objetos pero cambiamos la lógica con otro Visitor
            htmlBuilder.append(person.accept(htmlVisitor));
        }

        htmlBuilder.append("</div></body></html>");
        return htmlBuilder.toString();
    }
}