package org.locations.optiroute.repositories;

import org.locations.optiroute.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    Optional<AddressEntity> findByNAME(String name);

}
