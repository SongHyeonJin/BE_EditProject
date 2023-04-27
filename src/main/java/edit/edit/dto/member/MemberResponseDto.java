package edit.edit.dto.member;

import edit.edit.entity.JobEnum;
import edit.edit.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private String userId;
    private String nickname;
    private JobEnum job;

    public MemberResponseDto(Member member) {
        this.userId = member.getUserId();
        this.nickname = member.getNickname();
        this.job = member.getJob();
    }
}
