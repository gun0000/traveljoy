package com.traveljoy.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckIdDto {
    private String memberId;
    private boolean result;
}
