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
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public ResponseDto<List<BoardResponseDto>> list() {
        return boardService.list();
    }

    @PostMapping()
    public ResponseDto post(@RequestBody BoardRequestDto boardRequestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.save(boardRequestDto, userDetails.getMember());
    }

    @PutMapping("/{id}")
    public ResponseDto update(@PathVariable Long id,
                              @RequestBody BoardRequestDto boardRequestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.update(id, boardRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.delete(id, userDetails.getMember());
    }
}