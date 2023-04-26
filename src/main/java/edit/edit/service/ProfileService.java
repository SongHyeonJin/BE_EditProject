package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.profile.ProfileRequestDto;
import edit.edit.dto.profile.ProfileResponseDto;
import edit.edit.entity.Profile;
import edit.edit.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public ResponseDto save(ProfileRequestDto profileRequestDto) {
        Profile profile = profileRequestDto.toEntity();
        profileRepository.save(profile);

        return ResponseDto.setSuccess("profile save success", profile);
    }

    @Transactional(readOnly = true)
    public ResponseDto getProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow();
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(profile);
        return ResponseDto.setSuccess("getProfile success", profileResponseDto);
    }

    @Transactional
    public ResponseDto update(Long id, ProfileRequestDto profileRequestDto) {
        Profile profile = profileRepository.findById(id).orElseThrow();
        profile.updateProfile(profileRequestDto);

        return ResponseDto.setSuccess("profile update success", profile);
    }
}
