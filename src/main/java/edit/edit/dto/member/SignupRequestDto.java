package edit.edit.dto.member;

import edit.edit.entity.JobEnum;
import edit.edit.entity.Member;
import edit.edit.valid.EnumValid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupRequestDto {

    @Size(min = 4, max = 10)
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]*$", message = "{userId.pattern}")
    private String userId;

    @Size(min = 8, max = 15)
    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "{password.pattern}")
    private String password;

    @Email
    @NotBlank
    private String email;

    @Size(min = 2, max = 10)
    @NotBlank
    private String nickname;
    @EnumValid(message = "{job.enumvalid}")
    private JobEnum job;

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .job(job)
                .build();
    }
}
