package com.javarush.ramis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;

    @Column(name = "is_end", nullable = false)
    private boolean end;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Answer> answers;

    public Question(String description, Quest quest, List<Answer> answers, boolean end) {
        this.description = description;
        this.answers = answers;
        this.quest = quest;
        this.end = end;
    }
}
