package com.nnk.springboot.dto.rulename;

import com.nnk.springboot.domain.RuleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleNameResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
    private Boolean enabled;

    /** Converts this DTO to a RuleName entity for form binding. */
    public RuleName toRuleName() {
        return RuleName.builder()
                .id(id)
                .name(name)
                .description(description)
                .json(json)
                .template(template)
                .sqlStr(sqlStr)
                .sqlPart(sqlPart)
                .enabled(enabled != null ? enabled : true)
                .build();
    }
}
