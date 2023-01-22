package com.example.compileronlinebot.repository;

import com.example.compileronlinebot.entity.LangVersionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LangVersionRepository extends CrudRepository<LangVersionEntity, Integer> {

    List<LangVersionEntity> findByLanguage_NameOrderByIndex(String languageName);
}
