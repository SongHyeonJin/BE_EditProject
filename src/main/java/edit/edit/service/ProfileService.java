package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.profile.ProfileRequestDto;
import edit.edit.dto.profile.ProfileResponseDto;
import edit.edit.entity.JobEnum;
import edit.edit.entity.Member;
import edit.edit.entity.Preference;
import edit.edit.entity.Profile;
import edit.edit.repository.MemberRepository;
import edit.edit.repository.PreferenceRepository;
import edit.edit.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PreferenceRepository preferenceRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public ResponseDto<?> getProfile(Long memberId) {
        Profile profile = existProfile(memberId);
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(profile);
        return ResponseDto.setSuccess("getProfile success", profileResponseDto);
    }

    @Transactional
    public ResponseDto<?> update(Long id, ProfileRequestDto profileRequestDto, Member member) {
        Profile profile = existProfile(id);
        JobEnum job = member.getJob();
        isProfileMember(member, profile);

        // 프로필 정보 업데이트
        profile.updateProfile(profileRequestDto);

        // 선호도 계산
        List<Member> theOtherList = job.equals(JobEnum.YOUTUBER) ?
                memberRepository.findAllByJob(JobEnum.Editor) : memberRepository.findAllByJob(JobEnum.YOUTUBER);
        Map<Profile, Integer> personalRank = new HashMap<>();

        for (Member theOther:theOtherList) {
            Profile theOtherProfile = theOther.getProfile();
            personalRank.put(theOtherProfile,score(profile, theOtherProfile));
        }
        List<Profile> preferList = new ArrayList<>(personalRank.keySet()); // 아직 점수로 정렬되지 않은 상태


        Collections.sort(preferList, (o1, o2) -> (personalRank.get(o2).compareTo(personalRank.get(o1)))); // 점수로 정렬
        Preference updatedPreference = profile.getPreference();
        updatedPreference.updateProfileList(preferList);

        for(Profile p : preferList){
            System.out.println("-----ss-----"+p.getMember().getId());
            System.out.println("-----ss-----"+p.getMember().getJob());
        }

        profile.addPreference(updatedPreference);
        return ResponseDto.setSuccess("profile update success", profile);
    }

    private Profile existProfile(Long memberId){
        return profileRepository.findByMemberId(memberId).orElseThrow(
                () -> new NoSuchElementException("프로필을 작성한 회원이 없습니다.")
        );
    }

    private void isProfileMember(Member member, Profile profile){
        if(!profile.getMember().getUserId().equals(member.getUserId())){
            throw new IllegalArgumentException("일치하는 회원이 아닙니다.");
        }
    }

    // 개인별 선호도 점수(프로토 타입, 개량 필요)
    public int score(Profile myProfile, Profile theOtherProfile) {
        int score = 0;
        if (myProfile.getEditTool().equals(theOtherProfile.getEditTool())) score++;
        if (myProfile.getForm().equals(theOtherProfile.getForm())) score++;
        if (myProfile.getSalary() == theOtherProfile.getSalary()) score++;
        if (myProfile.getCareer().equals(theOtherProfile.getCareer())) score++;
        if (myProfile.getGenre().equals(theOtherProfile.getGenre())) score++;
        if (myProfile.getSpecialty().equals(theOtherProfile.getSpecialty())) score++;
        if (myProfile.getWorkStyle().equals(theOtherProfile.getWorkStyle())) score++;

        return score;
    }
}
