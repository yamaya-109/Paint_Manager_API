package com.example.paintapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.paintapi.paint.Paint;
import com.example.paintapi.user.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PaintRepository extends JpaRepository<Paint, Long> {
    List<Paint> findByName(String name);

    List<Paint> findByNameContaining(String name);

    List<Paint> findByType(String type);

    List<Paint> findByTypeContaining(String type);

    List<Paint> findByCategory(String category);

    List<Paint> findByCategoryContaining(String category);

    List<Paint> findByRedBetweenAndGreenBetweenAndBlueBetween(
            int redStart, int redEnd,
            int greenStart, int greenEnd,
            int blueStart, int blueEnd);

    Optional<Paint> findByNameAndTypeAndRedAndGreenAndBlueAndUser(String name, String type, int red, int green,
            int blue, User user);

    void deleteByName(String name);

    void deleteByType(String type);

    void deleteByCategory(String category);

    @Modifying
    @Query("UPDATE Paint p SET p.amount = p.amount - :amount WHERE p.id = :id")
    void decreaseAmountById(@Param("id") Long id, @Param("amount") int amount);

    @Modifying
    @Query("UPDATE Paint p SET p.amount = p.amount - :amount WHERE p.name = :name")
    void decreaseByName(@Param("name") String name, @Param("amount") int amount);

    List<Paint> findByNameAndUser(String name, User user);

}
