package com.example.cinemaapi.dto.model.movie;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageDto implements Comparable {
    private Long id;

    private String language;

    private Long createdBy;
    private String createdDate;
    private Long modifiedBy;
    private String modifiedDate;

    @Override
    public int compareTo(Object o) {
        return this.getId().compareTo(((LanguageDto) o).getId());
    }
}
