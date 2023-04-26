package edit.edit.controller;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.profile.ProfileRequestDto;
import edit.edit.security.UserDetailsImpl;
import edit.edit.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/myprofile")
    public ResponseDto profilePost(@RequestBody ProfileRequestDto profileRequestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return profileService.save(profileRequestDto, userDetails.getMember());
    }

    @GetMapping("/profile/{id}")
    public ResponseDto getProfile(@PathVariable Long id){
        return profileService.getProfile(id);
    }

    @PutMapping("/myprofile/{id}")
    public ResponseDto profileUpdate(@PathVariable Long id,
                                     @RequestBody ProfileRequestDto profileRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        return profileService.update(id, profileRequestDto, userDetails.getMember());
    }

}
