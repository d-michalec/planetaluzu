package com.planetaluzu.headhones.port;

import com.planetaluzu.headhones.domain.Headphones;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeadphonesRepository {
    Headphones save(Headphones headphones);
    Optional <Headphones> findByHeadphonesId(Long headphonesId);
    void delete(Long headphonesId);
    void deleteAll();
    List<Headphones> findAll();
}
