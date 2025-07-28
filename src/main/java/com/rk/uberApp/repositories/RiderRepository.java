package com.rk.uberApp.repositories;

import com.rk.uberApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface RiderRepository extends JpaRepository<Rider,Long> {
}
