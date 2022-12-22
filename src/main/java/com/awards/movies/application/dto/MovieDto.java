package com.awards.movies.application.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    @NotNull
    private Long movieYear;
    @NotEmpty
    private String title;
    @NotEmpty
    private String studios;
    @NotEmpty
    private String producers;
    @NotNull
    private Boolean winner;

}
