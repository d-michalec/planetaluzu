package com.planetaluzu.headhones.port;


import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.infrastructure.mapper.HeadphonesMapper;
import com.planetaluzu.headhones.infrastructure.repository.JpaHeadphonesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class HeadphonesRepositoryImpl implements HeadphonesRepository {

    private final JpaHeadphonesRepository jpaHeadphonesRepository;
    private final HeadphonesMapper headphonesMapper;

    @Override
    public Headphones save(Headphones headphones) {
        return headphonesMapper.toDomain(
                jpaHeadphonesRepository.save(
                        headphonesMapper.toEntity(headphones)
                )
        );
    }

    @Override
    public Optional<Headphones> findByHeadphonesId(Long headphonesId) {
        return jpaHeadphonesRepository.findByHeadphonesId(headphonesId).map(headphonesMapper::toDomain);
    }

    @Override
    public void delete(Long headphonesId) {
        jpaHeadphonesRepository.deleteByHeadphonesId(headphonesId);
    }

    @Override
    public void deleteAll() {
        jpaHeadphonesRepository.deleteAllInBatch();
    }

    @Override
    public List<Headphones> findAll() {
        return jpaHeadphonesRepository.findAll().stream().map(headphonesMapper::toDomain).toList();
    }


}
