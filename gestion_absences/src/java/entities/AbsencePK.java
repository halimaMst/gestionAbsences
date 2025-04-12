/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author adhmin
 */
@Embeddable
public class AbsencePK implements Serializable {

    private int etudiantId;
    private int seanceId;

    public AbsencePK() {
    }

    public AbsencePK(int etudiantId, int seanceId) {
        this.etudiantId = etudiantId;
        this.seanceId = seanceId;
    }

    // Getters & Setters
    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    public int getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(int seanceId) {
        this.seanceId = seanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbsencePK)) {
            return false;
        }
        AbsencePK that = (AbsencePK) o;
        return etudiantId == that.etudiantId && seanceId == that.seanceId;
    }

    @Override
    public int hashCode() {
        return 31 * etudiantId + seanceId;
    }
}
