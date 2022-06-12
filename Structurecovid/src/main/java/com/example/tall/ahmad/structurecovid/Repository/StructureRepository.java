package com.example.tall.ahmad.structurecovid.Repository;

import com.example.tall.ahmad.structurecovid.Entities.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureRepository extends JpaRepository<Structure,Long> {
    Structure findById(long struct_id);
}
