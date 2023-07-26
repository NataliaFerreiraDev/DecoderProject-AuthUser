package br.com.decoder.ead.authuser.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InstructorDto {

    @NotNull
    private UUID userId;

}
