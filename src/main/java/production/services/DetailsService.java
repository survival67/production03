package production.services;

import org.springframework.stereotype.Service;
import production.model.Details;
import production.repository.DetailsRepository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

@Service
public class DetailsService {

    private final DetailsRepository repository;

    public DetailsService(DetailsRepository repository) {
        this.repository = repository;
    }

    public List<Details> findAll() {
        return repository.findAll();
    }

    public List<Details> findByComponentId(Integer componentId) {
        return repository.findByComponentId(componentId);
    }
    
    public Details findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Details detail) {
        repository.save(detail);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}