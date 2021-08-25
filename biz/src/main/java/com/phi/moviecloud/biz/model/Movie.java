package com.phi.moviecloud.biz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    private Long id;
    @NotNull
    private Long userId;
    @NotBlank
    @Size(max = 80)
    private String title;
    private Instant createdAt;
}
