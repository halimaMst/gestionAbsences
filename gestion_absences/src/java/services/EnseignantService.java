package services;

import dao.EnseignantDao;
import entities.Enseignant;
import java.util.List;

public class EnseignantService implements IService<Enseignant> {

    private final EnseignantDao ed;

    public EnseignantService() {
        this.ed = new EnseignantDao();
    }

    @Override
    public boolean create(Enseignant o) {
        return ed.create(o);
    }

    @Override
    public boolean update(Enseignant o) {
        return ed.update(o);
    }

    @Override
    public boolean delete(Enseignant o) {
        return ed.delete(o);
    }

    @Override
    public List<Enseignant> findAll() {
        return ed.findAll();
    }

    @Override
    public Enseignant findById(int id) {
        return ed.findById(id);
    }

    
}
