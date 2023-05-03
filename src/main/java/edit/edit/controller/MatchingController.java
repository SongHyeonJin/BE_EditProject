package edit.edit.controller;

import edit.edit.dto.ResponseDto;
import edit.edit.security.UserDetailsImpl;
import edit.edit.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping("/matching")
    public ResponseDto match(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return matchingService.matchingList(userDetails.getMember());
    }

}
