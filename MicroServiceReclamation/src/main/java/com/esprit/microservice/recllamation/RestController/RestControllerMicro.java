package com.esprit.microservice.recllamation.RestController;

import com.esprit.microservice.recllamation.entity.Reclamation;
import com.esprit.microservice.recllamation.entity.TypeReclamation;
import com.esprit.microservice.recllamation.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")  // Allow Angular frontend
@RestController
@RequestMapping("/reclamations")
public class RestControllerMicro {

    @Autowired
    private IService reclamationService;

    @PostMapping
    public Reclamation addReclamation(@RequestBody Reclamation reclamation) {
        return reclamationService.addReclamation(reclamation);
    }

    @PutMapping("/{id}")
    public Reclamation updateReclamation(@PathVariable Long id, @RequestBody Reclamation reclamation) {
        return reclamationService.updateReclamation(id, reclamation);
    }

    @DeleteMapping("/{id}")
    public void deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
    }

    @GetMapping("/{id}")
    public Reclamation getReclamationById(@PathVariable Long id) {
        return reclamationService.getReclamationById(id);
    }

    @GetMapping
    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

    @GetMapping("/newest")
    public List<Reclamation> getReclamationsByNewestDate() {
        return reclamationService.getReclamationsByNewestDate();
    }

    @GetMapping("/type/{type}")
    public List<Reclamation> getReclamationsByType(@PathVariable TypeReclamation type) {
        return reclamationService.getReclamationsByType(type);
    }
}