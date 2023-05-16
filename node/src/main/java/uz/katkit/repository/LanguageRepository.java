package uz.katkit.repository;

import org.springframework.data.repository.CrudRepository;
import uz.katkit.entity.LanguageEntity;


public interface LanguageRepository extends CrudRepository<LanguageEntity,Integer> {
}
