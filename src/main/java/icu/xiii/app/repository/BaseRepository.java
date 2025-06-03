package icu.xiii.app.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T,S> {

    void save(S request);

    Optional<List<T>> getAll();

    Optional<T> getById(Long id);

    void update(Long id, S request);

    boolean deleteById(Long id);
}
