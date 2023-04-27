package edit.edit.repository;

import edit.edit.entity.Board;
import edit.edit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByMember(Member member);
}
