package edit.edit.controller;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.board.BoardRequestDto;
import edit.edit.dto.board.BoardResponseDto;
import edit.edit.service.BoardService;
import lombok.RequiredArgsConstructor;
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
    public ResponseDto post(@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.save(boardRequestDto) ;
    }

    @PutMapping("/{id}")
    public ResponseDto update(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.update(id, boardRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        return boardService.delete(id);
    }
}