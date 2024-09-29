package com.example.bookshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookshop.models.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Query("SELECT t FROM Topic t WHERE LOWER(t.topicName) LIKE LOWER(CONCAT('%', :topicName, '%'))")
    Iterable<Topic> searchTopicByName(@Param("topicName") String topicName);

    Page<Topic> findByTopicNameContaining(String searchText, Pageable pageable);
}
