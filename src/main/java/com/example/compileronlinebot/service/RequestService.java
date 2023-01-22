package com.example.compileronlinebot.service;

import com.example.compileronlinebot.entity.RequestEntity;
import com.example.compileronlinebot.repository.RequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;


    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    public void addRequest(String code, Long userId, Integer messageId) {
        RequestEntity request = new RequestEntity();
        request.setCode(code);
        request.setUserId(userId);
        request.setMessageId(messageId);
        request.setLangVersionId(1);
        requestRepository.save(request);
    }


    public void updateLangVersion(String language, String index, Long userId) {

        requestRepository.updateLangVersion(language, Integer.valueOf(index), userId);

    }


    public RequestEntity getLastRequest(Long userId) {
        Pageable pageable = PageRequest.of(0, 1);
        Page<RequestEntity> page = requestRepository.findByUserIdOrderByIdDesc(userId, pageable);
        if (!page.hasContent()) {
            return null;
        }

        List<RequestEntity> content = page.getContent();
        return content.get(0);

    }
}
