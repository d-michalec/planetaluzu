package com.planetaluzu.event.port;

import com.planetaluzu.event.domain.Event;
import com.planetaluzu.event.infrastructure.entity.EventJpaEntity;
import com.planetaluzu.event.infrastructure.mapper.EventMapper;
import com.planetaluzu.event.infrastructure.repository.JpaEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {
    private final JpaEventRepository jpaEventRepository;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public Event save(Event event) {
        EventJpaEntity entity = eventMapper.toEntity(event);
        EventJpaEntity savedEntity = jpaEventRepository.save(entity);
        return eventMapper.toDomain(savedEntity);
    }


    @Override
    public void deleteById(Long id) {
        jpaEventRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        jpaEventRepository.deleteAllInBatch();
    }

    @Override
    public List<Event> findAll() {
        return jpaEventRepository.findAll().stream().map(eventMapper::toDomain).toList();
    }
}
