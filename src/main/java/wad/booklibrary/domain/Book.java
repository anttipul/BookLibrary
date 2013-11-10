/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "BOOKS")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "TITLE")
    private String title;

    @NotBlank
    @Column(name = "ISBN")
    private String isbn;

    
    @NotEmpty
    @Column(name="AUTHORS")
    @ElementCollection(targetClass = java.lang.String.class)
    private List<String> authors = new ArrayList<String>();
      
    @Column(name = "PUBLISHERS")
    private List<String> publishers = new ArrayList<String>();

    @Min(0)
    @Column(name = "PUBLISHINGYEAR")
    private Integer publishingYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
        this.publishingYear = publishingYear;
    }

}
