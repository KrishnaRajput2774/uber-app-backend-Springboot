package com.rk.uberApp.repositories;

import com.rk.uberApp.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
}
