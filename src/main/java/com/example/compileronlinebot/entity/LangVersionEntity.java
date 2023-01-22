package com.example.compileronlinebot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lang_version")
public class LangVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "language")
    private Integer languageId;
    @ManyToOne
    @JoinColumn(name = "language", insertable = false, updatable = false)
    private LanguageEntity language;

    @Column
    private Integer index;

    @Column
    private String name;
}
