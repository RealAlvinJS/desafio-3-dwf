package com.dwf.desafio_3.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inscripciones_deportistas")
public class InscripcionDeportista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_deportista", nullable = false, length = 100)
    private String nombreDeportista;

    @Column(name = "documento_identificacion", nullable = false, length = 20)
    private String documentoIdentificacion;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private RegistroEventos eventoId;

    @Column(name = "edad_deportista", nullable = false)
    private Integer edadDeportista;

    @Column(name = "condicion_medica_cronica", nullable = false)
    private Boolean condicionMedicaCronica;

    public InscripcionDeportista() {
    }

    public InscripcionDeportista(String nombreDeportista, String documentoIdentificacion, String telefono, RegistroEventos evento, Integer edadDeportista, Boolean condicionMedicaCronica) {
        this.nombreDeportista = nombreDeportista;
        this.documentoIdentificacion = documentoIdentificacion;
        this.telefono = telefono;
        this.eventoId = evento;
        this.edadDeportista = edadDeportista;
        this.condicionMedicaCronica = condicionMedicaCronica;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDeportista() {
        return nombreDeportista;
    }

    public void setNombreDeportista(String nombreDeportista) {
        this.nombreDeportista = nombreDeportista;
    }

    public String getDocumentoIdentificacion() {
        return documentoIdentificacion;
    }

    public void setDocumentoIdentificacion(String documentoIdentificacion) {
        this.documentoIdentificacion = documentoIdentificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public Integer getEdadDeportista() {
        return edadDeportista;
    }

    public void setEdadDeportista(Integer edadDeportista) {
        this.edadDeportista = edadDeportista;
    }

    public Boolean getCondicionMedicaCronica() {
        return condicionMedicaCronica;
    }

    public void setCondicionMedicaCronica(Boolean condicionMedicaCronica) {
        this.condicionMedicaCronica = condicionMedicaCronica;
    }

    public RegistroEventos getEventoId() {
        return eventoId;
    }

    public void setEventoId(RegistroEventos eventoId) {
        this.eventoId = eventoId;
    }
}
