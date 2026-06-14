package com.planetaluzu.headhones.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Headphones {
    private Long id;
    private final Long headphonesId;
    private HeadphonesStatus status;

    public Headphones(Long headphonesId) {
        this.headphonesId = headphonesId;
    }

    public boolean isAvailable() {
        return status == HeadphonesStatus.DOSTĘPNE;
    }

    public void markAsIssued() {
        this.status = HeadphonesStatus.PRZYPISANE;
    }

    public void markAsAvailable() {
        this.status = HeadphonesStatus.DOSTĘPNE;
    }
}
