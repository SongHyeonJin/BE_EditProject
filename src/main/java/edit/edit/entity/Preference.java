package edit.edit.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edit.edit.dto.member.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Entity
@NoArgsConstructor
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobEnum job;

    @JsonManagedReference
    @OneToMany(mappedBy = "preference", cascade = CascadeType.ALL)
    private List<Profile> profileList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    public Preference(SignupRequestDto signupRequestDto) {
        this.job = signupRequestDto.getJob();
    }

    public void addMember(Member member){
        this.member = member;
    }

    // 연관 관계 편의 메서드
    public void addProfile(Profile profile) {
        profileList.add(profile);
        profile.addPreference(this);
    }

    public void addDefaultPreferences(Profile profile) {
        profileList.add(profile);
    }

    public void updateProfileList(List<Profile> list) {
        this.profileList = list;
    }

}
