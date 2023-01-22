package com.example.compileronlinebot.repository;

import com.example.compileronlinebot.entity.RequestEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

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
}
