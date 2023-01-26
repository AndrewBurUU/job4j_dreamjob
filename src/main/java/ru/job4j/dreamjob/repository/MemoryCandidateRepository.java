package ru.job4j.dreamjob.repository;

import net.jcip.annotations.*;
import org.springframework.stereotype.*;
import ru.job4j.dreamjob.model.*;

import java.time.*;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final ConcurrentHashMap<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0,
                "Johnson", "Middle Java разработчик",
                LocalDateTime.now(), 1, 0));
        save(new Candidate(0,
                "Peterson", "Team Lead Java разработчик",
                LocalDateTime.now(), 2, 0));
        save(new Candidate(0,
                "Simpson", "Стажер Java разработчик",
                LocalDateTime.now(), 3, 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.getAndIncrement());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate)
                -> new Candidate(oldCandidate.getId(), candidate.getName(),
                candidate.getDescription(), candidate.getCreationDate(),
                candidate.getCityId(), candidate.getFileId())
        ) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
