package uz.katkit.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.katkit.entity.ProfileEntity;

import java.util.List;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    @Query("select userId from ProfileEntity " +
            "where role='ADMIN' ")
    List<Long> getAdminUserIdList();
}
