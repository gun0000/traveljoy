package com.traveljoy.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationShowDto {

    private String image;
    private Long id;
    private String name;
    private String email;
    private Long price;


}
