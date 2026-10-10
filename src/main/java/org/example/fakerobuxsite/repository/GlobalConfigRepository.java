package org.example.fakerobuxsite.repository;

import org.example.fakerobuxsite.model.GlobalConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface GlobalConfigRepository extends MongoRepository<GlobalConfig, String> {

    Optional<GlobalConfig> findByUsername(String username);

}