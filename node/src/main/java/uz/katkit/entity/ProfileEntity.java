package uz.katkit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.katkit.enums.ProfileRole;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Long userId;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileRole role = ProfileRole.USER;

    @Column
    private Boolean visible = true;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private String fullName;

    @Column
    private String username;

    @Column
    private String languageCode;

    @Column
    private Boolean isPremium;
}
