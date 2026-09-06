package com.jin.routina.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthMethodRepository extends JpaRepository<AuthMethod,Integer> {
    Optional<AuthMethod> findByUserAndAuthType(User user, AuthType authType);
}
