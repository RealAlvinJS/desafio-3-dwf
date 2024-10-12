package com.dwf.desafio_3.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "registro_eventos")
public class RegistroEventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_evento", nullable = false, unique = true, length = 100)
    private String nombreEvento;

    @Column(name = "fecha_inicio_evento", nullable = false)
    private LocalDate fechaInicioEvento;

    @Column(name = "fecha_fin_evento", nullable = false)
    private LocalDate fechaFinEvento;

    @Column(name = "hora_rango_evento", nullable = false)
    private LocalTime horaRangoEvento;

    @Column(name = "tipo_evento", nullable = false, length = 50)
    private String tipoEvento;

    @Column(name = "rango_edad", nullable = false, length = 10)
    private String rangoEdad;

    @Column(name = "requerimiento_salud", nullable = false)
    private Boolean requerimientoSalud;

    @Column(name = "costo_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoTotal;

    public RegistroEventos() {
    }

    public RegistroEventos(String nombreEvento, LocalDate fechaInicioEvento, LocalDate fechaFinEvento, LocalTime horaRangoEvento, String tipoEvento, String rangoEdad, Boolean requerimientoSalud, BigDecimal costoTotal) {
        this.nombreEvento = nombreEvento;
        this.fechaInicioEvento = fechaInicioEvento;
        this.fechaFinEvento = fechaFinEvento;
        this.horaRangoEvento = horaRangoEvento;
        this.tipoEvento = tipoEvento;
        this.rangoEdad = rangoEdad;
        this.requerimientoSalud = requerimientoSalud;
        this.costoTotal = costoTotal;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public LocalDate getFechaInicioEvento() {
        return fechaInicioEvento;
    }

    public void setFechaInicioEvento(LocalDate fechaInicioEvento) {
        this.fechaInicioEvento = fechaInicioEvento;
    }

    public LocalDate getFechaFinEvento() {
        return fechaFinEvento;
    }

    public void setFechaFinEvento(LocalDate fechaFinEvento) {
        this.fechaFinEvento = fechaFinEvento;
    }

    public LocalTime getHoraRangoEvento() {
        return horaRangoEvento;
    }

    public void setHoraRangoEvento(LocalTime horaRangoEvento) {
        this.horaRangoEvento = horaRangoEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getRangoEdad() {
        return rangoEdad;
    }

    public void setRangoEdad(String rangoEdad) {
        this.rangoEdad = rangoEdad;
    }

    public Boolean getRequerimientoSalud() {
        return requerimientoSalud;
    }

    public void setRequerimientoSalud(Boolean requerimientoSalud) {
        this.requerimientoSalud = requerimientoSalud;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }
}
