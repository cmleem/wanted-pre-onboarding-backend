package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.ApplicationDto;
import hello.wantedpreonboarding.entity.Application;
import hello.wantedpreonboarding.entity.Posting;
import hello.wantedpreonboarding.entity.User;
import hello.wantedpreonboarding.mapper.ApplicationMapper;
import hello.wantedpreonboarding.repository.ApplicationRepository;
import hello.wantedpreonboarding.repository.PostingRepository;
import hello.wantedpreonboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final PostingRepository postingRepository;

    public ApplicationDto createApplication(String username, Integer postingId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User with name " + username + " not found"));
        Posting posting = postingRepository.findById(postingId).orElseThrow(() -> new IllegalArgumentException("Posting with id " + postingId + " not found"));

        Optional<Application> found = applicationRepository.findByUser(user);
        if (found.isPresent()) {
            throw new IllegalArgumentException("Application with User id " + user.getId() + " already exists");
        }

        Application application = Application.builder()
                .user(user)
                .posting(posting)
                .build();
        Application saved = applicationRepository.save(application);
        return ApplicationMapper.toDto(saved);
    }

    public ApplicationDto readApplicationById(Integer id) {
        Application application = getApplication(id);
        return ApplicationMapper.toDto(application);
    }

    public Page<ApplicationDto> readApplicationList(Pageable pageable) {
        Page<Application> applications = applicationRepository.findAll(pageable);
        return applications.map(ApplicationMapper::toDto);
    }

    public void deleteApplication(Integer id) {
        Application application = getApplication(id);
        applicationRepository.delete(application);
    }

    private Application getApplication(Integer id) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Application with id " + id + " not found"));
        return application;
    }
}
