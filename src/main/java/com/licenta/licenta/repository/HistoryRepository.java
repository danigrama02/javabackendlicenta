package com.licenta.licenta.repository;

import com.licenta.licenta.domain.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface HistoryRepository extends MongoRepository<History, Integer> {
    ArrayList<History> findByUsername(String username);
}
