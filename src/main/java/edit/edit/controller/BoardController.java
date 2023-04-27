package edit.edit.controller;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.board.BoardRequestDto;
import edit.edit.dto.board.BoardResponseDto;
import edit.edit.security.UserDetailsImpl;
import edit.edit.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public ResponseDto<List<BoardResponseDto>> list() {
        return boardService.list();
    }

    @GetMapping("/myboard")
    public ResponseDto<List<BoardResponseDto>> myList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.myList(userDetails.getMember());
    }

    @GetMapping("/board/{id}")
    public ResponseDto<BoardResponseDto> findOne(@PathVariable Long id) {
        return boardService.findBoard(id);
    }

    @PostMapping("/board")
    public ResponseDto save(@RequestBody BoardRequestDto boardRequestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.save(boardRequestDto, userDetails.getMember());
    }

    @PutMapping("/board/{id}")
    public ResponseDto update(@PathVariable Long id,
                              @RequestBody BoardRequestDto boardRequestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.update(id, boardRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/board/{id}")
    public ResponseDto delete(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.delete(id, userDetails.getMember());
    }
}