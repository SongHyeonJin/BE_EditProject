package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.member.LoginRequestDto;
import edit.edit.dto.member.SignupRequestDto;
import edit.edit.entity.Member;
import edit.edit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        String userId = signupRequestDto.getUserId();
        String nickname = signupRequestDto.getNickname();
        String email = signupRequestDto.getEmail();

        //유효성 검사
        validExistId(userId);
        validExistNickname(nickname);
        validExistEmail(email);

        memberRepository.save(signupRequestDto.toEntity());
        return ResponseDto.setSuccess("회원가입 성공", null);
    }

    /**
     * 로그인
     */
    public ResponseDto login(LoginRequestDto loginRequestDto) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        //유효성 검사
        Member member = validateIsMember(userId);
        validatePassword(member, password);

        return ResponseDto.setSuccess("로그인 성공", null);
    }

    //====유효성 검사====

    private Member validateIsMember(String userId) {
        return memberRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("등록된 회원이 없습니다.")
        );
    }

    private void validExistId(String userId) {
        memberRepository.findByUserId(userId)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 ID입니다.");
                });
    }
    private void validExistNickname(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
                });
    }
    private void validExistEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("해당 이메일로 가입된 회원이 존재합니다.");
                });
    }

    private void validatePassword(Member member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
