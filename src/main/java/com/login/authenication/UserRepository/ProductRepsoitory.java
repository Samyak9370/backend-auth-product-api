package com.login.authenication.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login.authenication.Entity.Product;

public interface ProductRepsoitory extends JpaRepository<Product,Long> {
    
}
