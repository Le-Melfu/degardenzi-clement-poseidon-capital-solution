package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleNameCreateDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    private String json;

    private String template;

    private String sqlStr;

    private String sqlPart;

    private Boolean enabled;
}
