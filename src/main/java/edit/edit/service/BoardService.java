package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.board.BoardRequestDto;
import edit.edit.dto.board.BoardResponseDto;
import edit.edit.entity.Board;
import edit.edit.entity.Member;
import edit.edit.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * 조회
     */
    @Transactional(readOnly = true)
    public ResponseDto<List<BoardResponseDto>> list() {
        List<BoardResponseDto> boardResponseDtos = boardRepository.findAll().stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());

        return ResponseDto.setSuccess("list success", boardResponseDtos);
    }

    /**
     * 단일 조회
     */
    @Transactional(readOnly = true)
    public ResponseDto<BoardResponseDto> findBoard(Long id) {
        Board board = validateBoard(id);
        return ResponseDto.setSuccess("findBoard success", new BoardResponseDto(board));
    }

    /**
     * 등록
     */
    @Transactional
    public ResponseDto save(BoardRequestDto boardRequestDto, Member member) {
        Board board = boardRequestDto.toEntity();
        board.setMember(member);

        boardRepository.save(board);

        return ResponseDto.setSuccess("save success", null);
    }

    /**
     * 수정
     */
    @Transactional
    public ResponseDto update(Long id, BoardRequestDto boardRequestDto, Member member) {
        Board board = validateBoard(id);
        isBoardAuthor(member, board);
        board.updateBoard(boardRequestDto);

        return ResponseDto.setSuccess("update success", null);
    }

    /**
     * 삭제
     */
    @Transactional
    public ResponseDto delete(Long id, Member member) {
        // 추가 : 유저(Member)가 자신의 게시물을 삭제하려 하는지 확인해야함
        Board board = validateBoard(id);
        isBoardAuthor(member, board);
        boardRepository.deleteById(id);
        return ResponseDto.setSuccess("delete success", null);
    }

    //====유효성 검사====
    private Board validateBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("게시물이 존재하지 않습니다.")
        );
    }

    private void isBoardAuthor(Member member, Board board) {
        String boardMemberId = board.getMember().getUserId();
        if (!member.getUserId().equals(boardMemberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}