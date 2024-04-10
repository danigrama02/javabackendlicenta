package com.licenta.licenta.service;

import com.licenta.licenta.domain.History;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    public List<History> getHistory(String username) {
        //TODo add get history for specific user functionality
        return null;
    }

    public void uploadHistory(String username, History history) {
        //TODO add uplaod history functionality to the database
    }
}
