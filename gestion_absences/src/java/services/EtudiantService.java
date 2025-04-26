package services;

import dao.EtudiantDao;
import entities.Etudiant;
import java.util.List;

public class EtudiantService implements IService<Etudiant> {

    private final EtudiantDao ed;

    public EtudiantService() {
        this.ed = new EtudiantDao();
    }

    @Override
    public boolean create(Etudiant o) {
        return ed.create(o);
    }

    @Override
    public boolean update(Etudiant o) {
        return ed.update(o);
    }

    @Override
    public boolean delete(Etudiant o) {
        return ed.delete(o);
    }

    @Override
    public List<Etudiant> findAll() {
        return ed.findAll();
    }

    @Override
    public Etudiant findById(int id) {
        return ed.findById(id);
    }
}
