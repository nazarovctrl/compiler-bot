package uz.katkit.service;

import org.springframework.stereotype.Service;
import uz.katkit.dto.RequestShortInfoDTO;
import uz.katkit.mapper.IRequestShortInfo;
import uz.katkit.repository.RequestRepository;

import java.util.LinkedList;
import java.util.List;

@Service
public class RequestService {

    private final RequestRepository repository;

    public RequestService(RequestRepository repository) {
        this.repository = repository;
    }


    public List<RequestShortInfoDTO> getLanguageStatistic() {
        List<RequestShortInfoDTO> dtoList = new LinkedList<>();

        List<IRequestShortInfo> languageUsedCount = repository.getLanguageUsedCount();
        languageUsedCount.forEach(mapper -> {
            RequestShortInfoDTO dto = new RequestShortInfoDTO();
            dto.setLanguageName(mapper.getLanguageName());
            dto.setCount(mapper.getCount());
            dtoList.add(dto);
        });

        return dtoList;
    }

    public int getRequestCount() {
        return repository.getRequestCount();
    }
}
