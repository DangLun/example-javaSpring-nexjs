package com.example.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookshop.models.Book;
import com.example.bookshop.models.Topic;
import com.example.bookshop.repositories.TopicRepository;

@Service
public class TopicService {
    private final TopicRepository _topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this._topicRepository = topicRepository;
    }

    public Iterable<Topic> getAll() {
        Iterable<Topic> Topics = this._topicRepository.findAll();
        return Topics;
    }

    public Topic saveTopic(Topic topic) {
        return this._topicRepository.save(topic);
    }

    public Topic updateTopic(Topic newTopic) {
        Topic topic = this._topicRepository.findById(newTopic.getTopicId()).get();
        topic.setTopicName(newTopic.getTopicName());

        this._topicRepository.save(topic);
        return topic;
    }

    public Topic deleteTopic(Integer id) {
        Topic topic = this._topicRepository.findById(id).get();

        this._topicRepository.delete(topic);

        return topic;
    }

    public Topic getTopicById(Integer id) {
        Topic topic = this._topicRepository.findById(id).get();
        return topic;
    }

    public Iterable<Topic> searchTopicByName(String topicName) {
        Iterable<Topic> topics = _topicRepository.searchTopicByName(topicName);
        return topics;
    }

    public Page<Topic> searchTopics(String searchText, Pageable pageable) {
        return this._topicRepository.findByTopicNameContaining(searchText, pageable);
    }
}
