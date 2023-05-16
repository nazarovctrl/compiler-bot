package uz.katkit.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.katkit.entity.ProfileEntity;
import uz.katkit.enums.ProfileRole;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    boolean existsByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("update ProfileEntity " +
            "set visible=?2 " +
            "where userId=?1")
    void changeVisibleBuUserId(Long userId, boolean b);

    @Modifying
    @Transactional
    @Query("update ProfileEntity " +
            " set role=?2 " +
            " where userId=?1 ")
    void changeRole(Long chatId, ProfileRole role);
}
