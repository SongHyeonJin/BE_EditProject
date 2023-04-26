package edit.edit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String editTool;
    @Column(nullable = false)
    private String career;
    @Column(nullable = false)
    private String form;
    @Column(nullable = false)
    private int salary;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String workStyle;
    @Column(nullable = false)
    private String specialty;
    private String video;
    private String homepage;
    private String img;

}
