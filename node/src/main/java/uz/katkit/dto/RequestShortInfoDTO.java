package uz.katkit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestShortInfoDTO {
    private String code;
    private String result;
    private String langName;
    private String versionName;
    private LocalDateTime createdDate;
}
