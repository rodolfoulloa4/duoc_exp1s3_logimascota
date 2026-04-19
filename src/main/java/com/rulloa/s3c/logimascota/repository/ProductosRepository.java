package com.rulloa.s3c.logimascota.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rulloa.s3c.logimascota.model.Producto;

public interface ProductosRepository extends JpaRepository<Producto, Integer> {

}
