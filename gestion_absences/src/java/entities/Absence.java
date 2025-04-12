/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.*;

/**
 *
 * @author adhmin
 */
@Entity
@Table(name = "absences")
public class Absence {

    @EmbeddedId
    private AbsencePK id;

    @Column(name = "justifiee", nullable = false)
    private boolean justifiee;

    @ManyToOne
    @MapsId("etudiantId")
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @MapsId("seanceId")
    @JoinColumn(name = "seance_id")
    private SeanceCours seance;

    public Absence() {
    }

    public Absence(Etudiant etudiant, SeanceCours seance, boolean justifiee) {
        this.etudiant = etudiant;
        this.seance = seance;
        this.justifiee = justifiee;
        this.id = new AbsencePK(etudiant.getId(), seance.getId());
    }

    // Getters & Setters
    public AbsencePK getId() {
        return id;
    }

    public void setId(AbsencePK id) {
        this.id = id;
    }

    public boolean isJustifiee() {
        return justifiee;
    }

    public void setJustifiee(boolean justifiee) {
        this.justifiee = justifiee;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        if (this.id == null) {
            this.id = new AbsencePK();
        }
        this.id.setEtudiantId(etudiant != null ? etudiant.getId() : 0);
    }

    public SeanceCours getSeance() {
        return seance;
    }

    public void setSeance(SeanceCours seance) {
        this.seance = seance;
        if (this.id == null) {
            this.id = new AbsencePK();
        }
        this.id.setSeanceId(seance != null ? seance.getId() : 0);
    }
}
