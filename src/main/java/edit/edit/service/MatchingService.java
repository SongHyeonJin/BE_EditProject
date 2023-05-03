package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.profile.ProfileResponseDto;
import edit.edit.entity.JobEnum;
import edit.edit.entity.Member;
import edit.edit.entity.Preference;
import edit.edit.entity.Profile;
import edit.edit.repository.PreferenceRepository;
import edit.edit.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final ProfileRepository profileRepository;
    private final PreferenceRepository preferenceRepository;

    @Transactional(readOnly = true)
    public ResponseDto<List<ProfileResponseDto>> matchingList(Member member) {
        // 선호도 정보를 모두 가져와야 한다.
        // 선호도는 기본적으로 프로필의 리스트로 이루어져 있다.
        // 모든 유튜버의 선호도와 모든 편집자의 선호도를 가져와야 한다.
        // 유튜버 : 편집자1, 편집자2,...
        // 편집자 : 유튜버1, 유튜버2,... 이런 형태로 가지고 있으니,
        // 개념적으로는 2차원 배열 2개가 필요하다.
        List<Preference> youtuberList = member.getJob().equals(JobEnum.YOUTUBER)?
                preferenceRepository.findAllByJob(JobEnum.Editor) : preferenceRepository.findAllByJob(JobEnum.YOUTUBER);
        for (Preference preference:youtuberList) {
            for (Profile profile:preference.getProfileList()) {
                System.out.println("----------job?--------------:" + profile.getMember().getJob());
            }
        }
        Profile[][] youtuberPreference = new Profile[youtuberList.size()][youtuberList.size()];
        for(int i=0; i< youtuberList.size(); i++){
            youtuberPreference[i] = youtuberList.get(i).getProfileList().toArray(new Profile[0]);

        }
        System.out.println(Arrays.deepToString(youtuberPreference));
        for (Profile[] row:youtuberPreference) {
            System.out.println("----------row------------" + Arrays.toString(row));
            for (Profile profile:row) {
                System.out.println("elt: " + profile.getMember().getJob());
                System.out.println("elt: " + profile.getMember().getId());
            }
        }

        return null;
    }

    public void matching(Profile[][] youtuberPreference, Profile[][] editorPreference) {

        // 일단은 유튜버와 에디터가 각각 n명, n명인 경우에 대해서 작성
        int youtubers = editorPreference.length;
        int editors = youtuberPreference.length;

        // 게일-섀플리 알고리즘에서는 요청을 하는 쪽에 유리하게 매칭이 이루어진다는 이야기가 있었던 것 같은데
        // 어떤 의미에서 유리한 것인지..? 일단 유튜버가 요청(고백)을 하는 것으로 작성해보자.
        Queue<Profile> request = new LinkedList<>(Arrays.asList(editorPreference[0]));

        Map<Profile, Profile> matchings = new HashMap<>();

//        while (request.size() != 0) {
//            Profile confession = request.peek(); // 이번에 고백할 남자
//            // 고백할 남자가 현재 가장 좋아하는 여자
//            int bestOption = 0;
//            for (int i:youtuberPreference[confession - 1]) {
//                if (i != 0) {
//                    bestOption = i;
//                    break;
//                }
//            }
//            // 싱글이었던 두 남녀가 매칭되는 경우
//            if (!hasLover(matchings, bestOption)) {
//                matchings[request.poll() - 1] = bestOption; ;
//            } // 고백을 받은 여자가 싱글이 아닌 경우
//            else {
//                // 고백을 받은 여자의 현재 남자친구
//                int presentBf = getPresentBf(matchings, bestOption);
//                // 고백을 받은 여자가 기존의 남자보다 방금 고백받은 남자가 더 좋은 경우
//                for (int j:editorPreference[bestOption - 1]) {
//                    if (j == presentBf) {
//                        break;
//                    } else if (j == confession) {
//                        matchings[presentBf - 1] = 0; // 현재 남자친구와 헤어지고,
//                        matchings[request.poll() - 1] = bestOption; // 고백을 받아들임.
//                        request.add(presentBf); // 헤어진 남자는 싱글이 됨.
//                        break;
//                    }
//                }
//            }
//            matchedOrBrokeUp(youtuberPreference, confession);
//        }
//        for (int i = 0; i < N; i++) {
//            System.out.println(matchings[i]);
//        }
    }
    static boolean hasLover(int[] matchings, int bestOption) {
        for (int i: matchings) {
            if (i == bestOption) {
                return true;
            }
        }
        return false;
    }
    static void matchedOrBrokeUp(int[][] preference, int confession) {
        for (int i = 0; i < preference[confession - 1].length; i++) {
            if (preference[confession - 1][i] != 0) {
                preference[confession - 1][i] = 0;
                break;
            }
        }
    }
    static int getPresentBf(int[] matchings, int k) {
        int presentBf = 0;
        for (int i = 0; i < matchings.length; i++) {
            if (matchings[i] == k) {
                presentBf = i + 1;
            }
        }
        return presentBf;
    }
}
