package com.github.fabriciolfj.catalago.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
@EqualsAndHashCode(callSuper = true)
public class Book extends PersistableEntity{

    @NotBlank(message = "The book ISBN must be define.")
    @Pattern(regexp = "^(97([89]))?\\d{9}(\\d|X)$", message = "The ISBN formt must follow the standards ISBN-10 or ISBN-13")
    private String isbn;
    @NotBlank(message = "The book title must be defined.")
    private String title;
    @NotBlank(message = "The book author must be defined")
    private String author;
    @PastOrPresent(message = "The book cannot have been published in the future.")
    @Column(name = "publishing_year")
    private Year publishingYear;
    private BigDecimal price;
    @CreatedDate
    private Long createdDate;
    @LastModifiedDate
    private Long lastModifiedDate;
    @Version
    private int version;

    private String publisher;
}
