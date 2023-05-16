package uz.katkit.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMessageDTO {
    private String caption;
    private String fileName;
    private String path;
    private Long chatId;
    private Integer replyMessageId;
}
