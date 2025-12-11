# 🏢 HR Management System - Visitor Pattern Demo

Una aplicación web desarrollada con **Spring Boot** que simula un sistema de gestión de Recursos Humanos. Este proyecto sirve como demostración práctica del **Patrón de Diseño Visitor**, mostrando cómo gestionar operaciones complejas sobre una plantilla heterogénea (Empleados Fijos, Freelancers y Becarios) de forma modular y desacoplada.

## 🚀 Funcionalidades Principales

El sistema permite realizar 4 operaciones distintas sobre la misma lista de empleados sin modificar sus clases:

1.  **📊 Dashboard Visual:** Generación de tarjetas HTML dinámicas con el desglose de costes (Salario vs Impuestos/Gastos).
2.  **💰 Cálculo Financiero:** Motor de nóminas que calcula el coste real para la empresa aplicando reglas fiscales específicas por tipo de perfil.
3.  **📈 Gestión Salarial (Interactivo):** Funcionalidad para aplicar subidas de sueldo masivas (ej. 10% por inflación) modificando los objetos en tiempo real.
4.  **📥 Exportación de Datos:** Generación de reportes detallados en CSV compatibles con Excel (formato regional España: `;` como separador y `,` decimal).

## 🛠️ Stack Tecnológico

* **Java 17+**
* **Spring Boot 3.x** (Web)
* **Maven** (Gestión de dependencias)
* **Lombok** (Reducción de boilerplate)

## 🧠 El Patrón Visitor en este Proyecto

El núcleo del proyecto utiliza el patrón de comportamiento **Visitor** para separar los datos (Modelos) de la lógica de negocio (Visitantes).

### Estructura:
* **Elementos (`model`):** Clases simples (`FixedEmployee`, `Freelancer`, `Intern`) que solo almacenan datos.
* **Visitantes (`visitor`):** Clases que contienen la inteligencia:
    * `HtmlDashboardVisitor`: Pinta la interfaz web.
    * `PayrollVisitor`: Calcula los costes totales.
    * `SalaryIncreaseVisitor`: Modifica los salarios (Visitor con efectos secundarios).
    * `CsvExportVisitor`: Formatea los datos para exportación.

**Ventaja Clave:** Podemos añadir nuevas funcionalidades (ej. exportar a PDF) creando una nueva clase Visitor sin tocar ni romper el código de los empleados existentes.

## 📦 Cómo Ejecutar el Proyecto

1.  Clona este repositorio.
2.  Compila y ejecuta con Maven:
    ```bash
    mvn spring-boot:run
    ```
3.  Abre tu navegador y accede al Dashboard:
    ```
    http://localhost:8080/api/dashboard
    ```

## 📂 Estructura del Código

```text
com.example.hrsystem
  ├── controller    # HrController (Punto de entrada Web)
  ├── model         # Clases de datos (FixedEmployee, Freelancer...)
  ├── visitor       # Interfaces y Lógica (Los 4 Visitors)
  └── HrSystemApplication.java