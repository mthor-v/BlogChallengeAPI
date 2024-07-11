package com.mthor.blogchallenge.domain.entity;

import com.mthor.blogchallenge.domain.dto.post.UpdatePostDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "post")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 300)
    private String content;

    private LocalDateTime createdAt;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @PrePersist
    private void getCreatedDate(){
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public void updateData(UpdatePostDTO updatePostDTO, Category category) {
        if (updatePostDTO.title()!= null) {
            this.title= updatePostDTO.title();
        }
        if (updatePostDTO.content() != null) {
            this.content = updatePostDTO.content();
        }
        if (updatePostDTO.categoryId() != null) {
            this.category = category;
        }
    }

    public void changeState() {
        this.active = false;
    }
}