package edit.edit.repository;

import edit.edit.entity.JobEnum;
import edit.edit.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    List<Preference> findAllByJob(JobEnum job);
}
