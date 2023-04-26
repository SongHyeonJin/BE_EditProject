package edit.edit.dto.board;

import edit.edit.entity.Board;
import lombok.Getter;

import java.util.Date;

@Getter
public class BoardRequestDto {
    private String title;
    private String content;
    private String genre;
    private String career;
    private String quest;
    private Date deadline;
    private String form;
    private int salary;

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .genre(genre)
                .career(career)
                .quest(quest)
                .deadline(deadline)
                .form(form)
                .salary(salary)
                .build();
    }
}
