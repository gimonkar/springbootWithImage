package com.ImageProjects.ImageDemo01.Repository;

import com.ImageProjects.ImageDemo01.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
