package uz.katkit.mapper;

import java.time.LocalDateTime;

public interface IRequestMapper {
    String getCode();

    String getResult();

    String getLanguageName();

    String getVersionName();

    LocalDateTime getCreatedDate();
}
