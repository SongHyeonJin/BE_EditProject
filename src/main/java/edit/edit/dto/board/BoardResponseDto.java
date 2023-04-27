package edit.edit.dto.board;

import edit.edit.dto.member.MemberResponseDto;
import edit.edit.entity.Board;
import edit.edit.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String genre;
    private String career;
    private String quest;
    private Date deadline;
    private String form;
    private int salary;
    private MemberResponseDto member;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.genre = board.getGenre();
        this.career = board.getCareer();
        this.quest = board.getQuest();
        this.deadline = board.getDeadline();
        this.form = board.getForm();
        this.salary = board.getSalary();
        this.member = new MemberResponseDto(board.getMember());
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
