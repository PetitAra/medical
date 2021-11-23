package fr.m2i.medical.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "rdv", schema = "patients", catalog = "")
public class RdvEntity {

    private int id;
    private PatientEntity patient;
    private Timestamp dateHeure;
    private Integer duree;
    private String note;
    private String type;

    public RdvEntity() {
    }

    public RdvEntity(PatientEntity patient, Timestamp dateHeure, Integer duree, String note, String type) {
        this.patient = patient;
        this.dateHeure = dateHeure;
        this.duree = duree;
        this.note = note;
        this.type = type;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "patient", referencedColumnName = "id")
    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public Timestamp getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Timestamp dateHeure) {
        this.dateHeure = dateHeure;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdvEntity rdvEntity = (RdvEntity) o;
        return id == rdvEntity.id && duree == rdvEntity.duree && Objects.equals(patient, rdvEntity.patient) && Objects.equals(dateHeure, rdvEntity.dateHeure) && Objects.equals(note, rdvEntity.note) && Objects.equals(type, rdvEntity.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient, dateHeure, duree, note, type);
    }
}
