package com.traveljoy.room.mapper;

import com.traveljoy.room.dto.LocationDto;
import com.traveljoy.room.dto.ThemeDto;
import com.traveljoy.room.entity.Location;
import com.traveljoy.room.entity.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
    List<LocationDto> toLocationDtos(List<Location> locations);
    List<ThemeDto> toThemeDtos(List<Theme> themes);
}
