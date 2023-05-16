package uz.katkit.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.katkit.entity.ProfileEntity;


public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {

}
