package uz.katkit.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.katkit.entity.RequestEntity;
import uz.katkit.mapper.IRequestShortInfo;

import java.util.List;


public interface RequestRepository extends PagingAndSortingRepository<RequestEntity, Integer>, CrudRepository<RequestEntity, Integer> {


    @Query(" select r.langVersion.language.name as languageName , count(r.langVersion.language.name) as count from RequestEntity r " +
            "group by r.langVersion.language.name " +
            "order by count(r.langVersion.language.name) desc ")
    List<IRequestShortInfo> getLanguageUsedCount();

    @Query("select count(r.id) from RequestEntity r ")
    int getRequestCount();

}
