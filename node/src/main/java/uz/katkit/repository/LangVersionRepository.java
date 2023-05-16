package uz.katkit.repository;

import org.springframework.data.repository.CrudRepository;
import uz.katkit.entity.LangVersionEntity;

import java.util.List;

public interface LangVersionRepository extends CrudRepository<LangVersionEntity, Integer> {

    List<LangVersionEntity> findByLanguage_NameOrderByIndex(String languageName);
}
