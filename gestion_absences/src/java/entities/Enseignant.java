/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity

public class Enseignant extends User {

    public Enseignant() {
    }

    public Enseignant(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }

}
