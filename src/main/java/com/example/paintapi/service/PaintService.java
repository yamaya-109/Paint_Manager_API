package com.example.paintapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.paintapi.dto.PaintDto;
import com.example.paintapi.paint.Paint;
import com.example.paintapi.user.User;

public interface PaintService {
    Paint add(PaintDto dto);

    List<Paint> searchByName(String name);

    List<Paint> searchByNameContaining(String name);

    List<Paint> searchByCategory(String category);

    List<Paint> searchByCategoryContaining(String category);

    List<Paint> searchByType(String type);

    List<Paint> searchByTypeContaining(String type);

    List<Paint> searchSimilarColor(int r, int g, int b, int margin);

    List<Paint> getAllPaints();

    void mergeDuplicates();

    void deleteById(Long id);

    void deleteByName(String name);

    void deleteByType(String type);

    void deleteByCategory(String category);

    void deleteAll();

    Paint rebuildWithAdjustedAmount(Long id, int amount);

    void decreaseByName(String name, int amount);

    List<Paint> findByNameAndOownUser(String name, User user);

    public ResponseEntity<String> importPaints(MultipartFile file);

    byte[] exportPaintsAsJson();

}
