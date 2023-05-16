package uz.katkit.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.katkit.entity.RequestEntity;
import uz.katkit.mapper.IRequestMapper;

import java.time.LocalDate;
import java.util.List;

public interface RequestRepository extends PagingAndSortingRepository<RequestEntity, Integer>, CrudRepository<RequestEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update request " +
            " set lang_version=(select l.id from lang_version as l " +
            " where language=(select id from language where name=?1) and l.index=?2 ) " +
            " where  (select r.id from request as r " +
            " where r.user_id=?3 " +
            " order by r.id desc limit 1 ) = id ",
            nativeQuery = true)
    int updateLangVersion(String language, Integer index, Long userId);


    Page<RequestEntity> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "update RequestEntity set result=?2,compiled=true where id=?1 ")
    void saveResult(Integer requestId, String result);


    @Query("select r.code as code, r.result as result ,r.langVersion.name as versionName," +
            " r.langVersion.language.name as languageName , r.createdDate as createdDate " +
            "from RequestEntity r " +
            "where r.compiled=true and r.visible=true and r.userId=?1")
    List<IRequestMapper> findByUserId(Long userId);

    @Query("select r.code as code, r.result as result ,r.langVersion.name as versionName," +
            " r.langVersion.language.name as languageName , r.createdDate as createdDate " +
            "from RequestEntity r " +
            "where r.compiled=true and r.visible=true and r.userId=?1 and  cast(r.createdDate as date)=?2")
    List<IRequestMapper> findByUserIdAndCreatedDate(Long userId, LocalDate localDate);

    @Transactional
    @Modifying
    @Query("update RequestEntity " +
            " set visible=false " +
            "where userId=?1 and visible=true")
    int deleteAllRequestsByUserId(Long userId);


    @Transactional
    @Modifying
    @Query("update RequestEntity  " +
            "set visible=false " +
            "where userId=?1 and visible=true and cast(createdDate as date)=?2")
    int deleteAllRequestsByUserIdAndLocalDate(Long userId, LocalDate localDate);
}
