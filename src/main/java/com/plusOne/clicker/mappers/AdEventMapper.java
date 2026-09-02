package com.plusOne.clicker.mappers;

import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.requests.AdEventRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdEventMapper {

    AdEvent toDomain(AdEventRequest request);
}