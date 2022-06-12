package com.example.tall.ahmad.structurecovid.Controler;

import com.example.tall.ahmad.structurecovid.Entities.Structure;
import com.example.tall.ahmad.structurecovid.Repository.StructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/Covid")
public class StructureController {
    @Autowired
    private StructureRepository structuresRepository;
    @GetMapping(value = "/structures")
    public List<Structure> getAll() {

        List<Structure> structuresList = structuresRepository.findAll();
        Collections.sort(structuresList);

        return structuresList;
    }

    @PostMapping(path = "/structures")
    public ResponseEntity<Object> add(@RequestBody Structure structures) {

        Structure structures1 = structuresRepository.save(structures);

        if(structures1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("")
                .buildAndExpand(structures1.getStruct_id())
                .toUri();

        return ResponseEntity.status(201).body("{'new_struct_link':"+location+"}");
    }
    @PutMapping(path= "/structures/{struct_id}")
    public ResponseEntity<Object> update(@PathVariable long struct_id, @RequestBody Structure structures) {

        Structure structures1 = structuresRepository.findById(struct_id);

        if(structures1 == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{'Erreur': 'Cette structures n'existe pas !'}");

        structures.setStruct_id(structures1.getStruct_id());
        structures1 = structuresRepository.save(structures);
        if(structures1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(structures1.getStruct_id())
                .toUri();

        return ResponseEntity.ok(location);
    }
    @GetMapping("/structures/{struct_id}")
    public ResponseEntity<Object> getAll(@PathVariable long struct_id) {
        Structure structures = structuresRepository.findById(struct_id);
        if (structures == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{'Erreur': 'Cette structures n'existe pas !'}");


        URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/rvs/{id}/structures")
                .buildAndExpand(structures.getStruct_id())
                .toUri();

        structures.setStruct_rv_list(location.toString());

        return ResponseEntity.ok(structures);
    }


}
