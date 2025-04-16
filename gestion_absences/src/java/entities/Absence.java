/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author adhmin
 */
@Entity
@Table(name = "Absences")
public class Absence {

    @EmbeddedId
    private AbsenceId id;

    @ManyToOne
    @JoinColumn(name = "seance_cours_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SeanceCours seanceCours;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Etudiant etudiant;

    public Absence() {
    }

    public Absence(SeanceCours seanceCours, Etudiant etudiant) {
        this.seanceCours = seanceCours;
        this.etudiant = etudiant;
    }

    public AbsenceId getId() {
        return id;
    }

    public void setId(AbsenceId id) {
        this.id = id;
    }

    public SeanceCours getSeanceCours() {
        return seanceCours;
    }

    public void SeanceCours(SeanceCours seanceCours) {
        this.seanceCours = seanceCours;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public String toString() {
        return "Absence{" + "id=" + id + ", seanceCours=" + seanceCours + ", etudiant=" + etudiant + '}';
    }

}
