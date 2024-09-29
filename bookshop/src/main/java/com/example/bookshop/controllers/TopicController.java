package com.example.bookshop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.models.Customer;
import com.example.bookshop.models.Topic;
import com.example.bookshop.services.TopicService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/topic")
public class TopicController {
    private final TopicService _topicService;

    public TopicController(TopicService topicService) {
        this._topicService = topicService;
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<Topic> getAllTopic() {
        return this._topicService.getAll();
    }

    @PostMapping("/addTopic")
    public @ResponseBody Topic addTopic(@RequestBody Topic topic) {
        return this._topicService.saveTopic(topic);
    }

    @PutMapping("/updateTopic")
    public @ResponseBody Topic updateTopic(@RequestBody Topic topic) {
        return this._topicService.updateTopic(topic);
    }

    @DeleteMapping("/deleteTopic/{id}")
    public @ResponseBody Topic deleteTopic(@PathVariable Integer id) {
        return this._topicService.deleteTopic(id);
    }

    @GetMapping("/get-topic-by-id/{id}")
    public @ResponseBody Topic getTopicById(@PathVariable Integer id) {
        return this._topicService.getTopicById(id);
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<?> searchTopicByName(@RequestParam String topicName) {
        try {
            Iterable<Topic> topics = this._topicService.searchTopicByName(topicName);
            return ResponseEntity.status(HttpStatus.CREATED).body(topics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Topic>> searchTopics(
            @RequestParam(defaultValue = "") String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "customerName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<Topic> topicPage = _topicService.searchTopics(searchText, pageable);
        return new ResponseEntity<>(topicPage, HttpStatus.OK);
    }
}
