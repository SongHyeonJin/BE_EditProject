package edit.edit.dto.profile;

import edit.edit.entity.Profile;
import lombok.Getter;

@Getter
public class ProfileRequestDto {
    private String editTool;
    private String career;
    private String form;
    private int salary;
    private String genre;
    private String workStyle;
    private String specialty;
    private String video;
    private String homepage;
    private String img;

    public Profile toEntity(){
        return Profile.builder()
                .editTool(editTool)
                .career(career)
                .form(form)
                .salary(salary)
                .genre(genre)
                .workStyle(workStyle)
                .specialty(specialty)
                .video(video)
                .homepage(homepage)
                .img(img)
                .build();
    }
}
