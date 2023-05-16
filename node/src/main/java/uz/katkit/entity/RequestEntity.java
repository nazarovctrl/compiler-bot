package uz.katkit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "request")
@Getter
@Setter
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Long userId;

    @Column(columnDefinition = "text")
    private String code;

    @Column(columnDefinition = "text")
    private String result;

    @Column
    private Integer messageId;

    @Column(name = "lang_version")
    private Integer langVersionId;
    @JoinColumn(name = "lang_version", insertable = false, updatable = false)
    @ManyToOne
    private LangVersionEntity langVersion;


    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();


    @Column
    private Boolean visible = true;

    @Column
    private Boolean compiled = false;
}
