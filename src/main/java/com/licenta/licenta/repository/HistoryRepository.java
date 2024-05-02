package com.licenta.licenta.repository;

import com.licenta.licenta.domain.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends MongoRepository<History, Integer> {
    ArrayList<History> findByUsername(String username);
}
