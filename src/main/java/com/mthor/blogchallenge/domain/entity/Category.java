package com.mthor.blogchallenge.domain.entity;

import com.mthor.blogchallenge.domain.dto.category.CreateCategoryDTO;
import com.mthor.blogchallenge.domain.dto.category.UpdateCategoryDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "category")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 150)
    private String description;

    private Boolean active;

    @PrePersist
    private void initState() {
        active = true;
    }

    public Category(CreateCategoryDTO data) {
        this.name = data.category();
        this.description = data.description();
    }

    public void changeState(){
        active = false;
    }

    public void updateData(UpdateCategoryDTO data) {
        if(data.category() != null ){
            this.name = data.category();
        }
        if(data.description() != null ){
            this.description = data.description();
        }
    }

}
