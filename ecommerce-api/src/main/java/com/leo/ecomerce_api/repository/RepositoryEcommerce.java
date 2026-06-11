package com.leo.ecomerce_api.repository;


import com.leo.ecomerce_api.entity.EntityEcommerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEcommerce extends JpaRepository<EntityEcommerce, Long> {


}
