package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.profile.ProfileRequestDto;
import edit.edit.dto.profile.ProfileResponseDto;
import edit.edit.entity.Member;
import edit.edit.entity.Profile;
import edit.edit.jwt.JwtUtil;
import edit.edit.repository.MemberRepository;
import edit.edit.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto save(ProfileRequestDto profileRequestDto, Member member) {
        Profile profile = profileRequestDto.toEntity();
        profileRepository.save(profile);

        profile.addMember(member);
        return ResponseDto.setSuccess("profile save success", profile);
    }

    @Transactional(readOnly = true)
    public ResponseDto getProfile(Long id) {
        Profile profile = existProfile(id);
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(profile);
        return ResponseDto.setSuccess("getProfile success", profileResponseDto);
    }

    @Transactional
    public ResponseDto update(Long id, ProfileRequestDto profileRequestDto, Member member) {
        Profile profile = existProfile(id);
        isProfileMember(member, profile);
        profile.updateProfile(profileRequestDto);

        return ResponseDto.setSuccess("profile update success", profile);
    }

    private Profile existProfile(Long id){
        return profileRepository.findById(id).orElseThrow();
    }

    private void isProfileMember(Member member, Profile profile){
        if(!profile.getMember().getUserId().equals(member.getUserId())){
            throw new IllegalArgumentException("일치하는 회원이 아닙니다.");
        }
    }

}
