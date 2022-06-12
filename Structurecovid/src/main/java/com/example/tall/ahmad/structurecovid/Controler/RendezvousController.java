package com.example.tall.ahmad.structurecovid.Controler;

import com.example.tall.ahmad.structurecovid.Entities.Redezvous;
import com.example.tall.ahmad.structurecovid.Entities.Structure;
import com.example.tall.ahmad.structurecovid.Repository.RedezVousRepository;
import com.example.tall.ahmad.structurecovid.Repository.StructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/Covid")
public class RendezvousController {
    @Autowired
    private RedezVousRepository rvsRepository;

    @Autowired
    private StructureRepository structuresRepository;

    @PostMapping(value = "/redezvous")
    public ResponseEntity<Object> add(@RequestBody Redezvous  rvs) {

        Redezvous  rvs1 = rvsRepository.save(rvs);

        if(rvs1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rvs1.getRv_id())
                .toUri();

        Structure structures = structuresRepository.findById(rvs.getStructures().getStruct_id());
        System.out.println(structures.getNom());

        // structures.setNbr_vaccin_dispo();
        if (rvs.isObjet()) {
            structures.setNbr_test_dispo(structures.getNbr_test_dispo() - 1);
        } else {
            structures.setNbr_vaccin_dispo(structures.getNbr_vaccin_dispo() - 1);
        }

        structuresRepository.save(structures);

        return ResponseEntity.status(201).body("{'new_rv_link':"+location+"}");
    }

    @PutMapping(value = "/redezvous/{rv_id}")
    public ResponseEntity<Object> update(@PathVariable long rv_id, @RequestBody Redezvous  rvs) {

        Redezvous  rvs1 = rvsRepository.findById(rv_id);

        if(rvs1 == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{'Erreur': 'Ce rendez-vous n'existe pas !'}");

        rvs.setRv_id(rvs1.getRv_id());
        rvs1 = rvsRepository.save(rvs);
        if(rvs1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(rvs1.getRv_id())
                .toUri();

        return ResponseEntity.ok(location);
    }

    @GetMapping(value = "/redezvous")
    public List<Redezvous> getAll() {
        return rvsRepository.findAll();
    }

    @GetMapping(value = "/redezvous/{rvs_id}")
    public ResponseEntity<Object> getAll(@PathVariable long rvs_id) {
        Redezvous  rvs = rvsRepository.findById(rvs_id);
        if (rvs == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{'Erreur': 'Ce rendez-vous n'existe pas !'}");


        URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/redezvous/{id}")
                .buildAndExpand(rvs.getRv_id()+1)
                .toUri();

        rvs.setNext_rv(location.toString());

        return ResponseEntity.ok(rvs);
    }

    @GetMapping(value = "/redezvous/{struct_id}/structures")
    public ResponseEntity<Object> getOneStructureRvs(@PathVariable long struct_id) {

        // TODO: Check if struct exit before

        List<Redezvous> userRvsList = rvsRepository.findAllByStructId(struct_id);

        // Get structs rvs
        return  ResponseEntity.ok(userRvsList);
    }

    @GetMapping(value = "/redezvous/{user_id}/users")
    public ResponseEntity<Object> getOneUserRvs(@PathVariable long user_id) {

        // TODO: Check if user exit before

        // Get user rvs
        List<Redezvous> userRvsList = rvsRepository.findAllByUserId(user_id);

        return  ResponseEntity.ok(userRvsList);
    }

}
