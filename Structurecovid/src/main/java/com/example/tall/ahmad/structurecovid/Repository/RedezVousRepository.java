package com.example.tall.ahmad.structurecovid.Repository;

import com.example.tall.ahmad.structurecovid.Entities.Redezvous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RedezVousRepository extends JpaRepository<Redezvous,Long> {
    Redezvous findById(long rvs_id);

    @Query(value = "SELECT * FROM redezvous r, structure s, utilisateur u WHERE r.struct_id = s.struct_id and r.struct_id = r.user_id and r.struct_id = ?", nativeQuery = true)
    List<Redezvous> findAllByStructId(long struct_id);

    @Query(value = "SELECT * FROM redezvous r, structure s, utilisateur u WHERE r.struct_id = s.struct_id and r.user_id = u.user_id and r.user_id = ?", nativeQuery = true)
    List<Redezvous> findAllByUserId(long struct_id);
}
