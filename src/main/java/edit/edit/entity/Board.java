package edit.edit.entity;

import edit.edit.dto.board.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    @ColumnDefault("'무관'")
    private String genre;

    @Column
    @ColumnDefault("'경력없음'")
    private String career;

    @Column
    private String quest;

    @Column
    private Date deadline;

    @Column
    @ColumnDefault("'협의가능'")
    private String form;

    @Column
    @ColumnDefault("'협의가능'")
    private int salary;

    @ManyToOne
    @JoinColumn(name = "member_id") // nullable = false
    private Member member;

    @Builder
    public Board(String title, String content, String genre, String career, String quest, Date deadline, String form, int salary){
        this.title = title;
        this.content = content;
        this.genre = genre;
        this.career = career;
        this.quest = quest;
        this.deadline = deadline;
        this.form = form;
        this.salary = salary;
    }

    public void updateBoard(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.genre = boardRequestDto.getGenre();
        this.career = boardRequestDto.getCareer();
        this.quest = boardRequestDto.getQuest();
        this.deadline = boardRequestDto.getDeadline();
        this.form = boardRequestDto.getForm();
        this.salary = boardRequestDto.getSalary();
    }
}
