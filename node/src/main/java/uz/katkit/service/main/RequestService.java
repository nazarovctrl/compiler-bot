package uz.katkit.service.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.katkit.dto.RequestShortInfoDTO;
import uz.katkit.entity.RequestEntity;
import uz.katkit.mapper.IRequestMapper;
import uz.katkit.repository.RequestRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Service
public class RequestService {

    private final RequestRepository requestRepository;


    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    public void addRequest(String code, Long userId, Integer messageId) {
        RequestEntity request = new RequestEntity();
        request.setCode(code);
        request.setUserId(userId);
        request.setMessageId(messageId);
        request.setLangVersionId(1);
        requestRepository.save(request);
    }


    public void updateLangVersion(String language, String index, Long userId) {

        requestRepository.updateLangVersion(language, Integer.valueOf(index), userId);

    }


    public RequestEntity getLastRequest(Long userId) {
        Pageable pageable = PageRequest.of(0, 1);
        Page<RequestEntity> page = requestRepository.findByUserIdOrderByIdDesc(userId, pageable);
        if (!page.hasContent()) {
            return null;
        }

        List<RequestEntity> content = page.getContent();
        return content.get(0);

    }

    public void saveResult(String result, Integer requestId) {
        requestRepository.saveResult(requestId, result);
    }

    public List<RequestShortInfoDTO> getAllRequests(Long userId) {
        List<IRequestMapper> mapperList = requestRepository.findByUserId(userId);

        Function<IRequestMapper, RequestShortInfoDTO> function = (mapper) -> new RequestShortInfoDTO(
                mapper.getCode(), mapper.getResult(), mapper.getLanguageName(),
                mapper.getVersionName(), mapper.getCreatedDate()
        );

        return mapperList.stream().map(function).toList();

    }

    public List<RequestShortInfoDTO> getAllRequests(Long userId, LocalDate localDate) {
        List<IRequestMapper> mapperList = requestRepository.findByUserIdAndCreatedDate(userId, localDate);

        Function<IRequestMapper, RequestShortInfoDTO> function = (mapper) -> new RequestShortInfoDTO(
                mapper.getCode(), mapper.getResult(), mapper.getLanguageName(),
                mapper.getVersionName(), mapper.getCreatedDate()
        );

        return mapperList.stream().map(function).toList();

    }

    public int deleteAllRequests(Long userId) {
        return requestRepository.deleteAllRequestsByUserId(userId);
    }

    public int deleteAllRequests(Long userId, LocalDate localDate) {
        return requestRepository.deleteAllRequestsByUserIdAndLocalDate(userId, localDate);
    }
}
