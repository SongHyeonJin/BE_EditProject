package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.board.BoardRequestDto;
import edit.edit.dto.board.BoardResponseDto;
import edit.edit.entity.Board;
import edit.edit.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public ResponseDto<List<BoardResponseDto>> list() {

        List<BoardResponseDto> postResponseDtos = boardRepository.findAll().stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());

        return ResponseDto.setSuccess("list success", postResponseDtos);
    }

    @Transactional
    public ResponseDto save(BoardRequestDto boardRequestDto) {
        Board board = boardRequestDto.toEntity();
        boardRepository.save(board);

        return ResponseDto.setSuccess("save success", null);
    }

    @Transactional
    public ResponseDto update(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow();
        // 추가 : 유저(Member)가 자신의 게시물을 수정하려 하는지 확인해야함
        board.updateBoard(boardRequestDto);

        return ResponseDto.setSuccess("update success", null);
    }

    @Transactional
    public ResponseDto delete(Long id) {
        // 추가 : 유저(Member)가 자신의 게시물을 삭제하려 하는지 확인해야함
        if(!boardRepository.existsById(id)) return ResponseDto.setBadRequest("delete fail");

        boardRepository.deleteById(id);
        return ResponseDto.setSuccess("delete success", null);
    }
}