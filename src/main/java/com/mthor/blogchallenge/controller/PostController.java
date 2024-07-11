package com.mthor.blogchallenge.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mthor.blogchallenge.domain.dto.post.CreatePostDTO;
import com.mthor.blogchallenge.domain.dto.post.PostResponseDTO;
import com.mthor.blogchallenge.domain.dto.post.UpdatePostDTO;
import com.mthor.blogchallenge.domain.entity.Category;
import com.mthor.blogchallenge.domain.entity.Post;
import com.mthor.blogchallenge.domain.entity.User;
import com.mthor.blogchallenge.domain.service.ICategoryService;
import com.mthor.blogchallenge.domain.service.IPostService;
import com.mthor.blogchallenge.domain.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Value("${api.security.secret}")
    private String apiSecret;

    @Autowired
    private IPostService postService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody @Valid CreatePostDTO body, @RequestHeader HttpHeaders headers,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        String token = Objects.requireNonNull(headers.get("Authorization")).get(0);
        String sub = getSubjectFromToken(token);
        User user = userService.getUserByEmail(sub);
        Category category = categoryService.getCategoryById(body.categoryId());
        Post post = postService.createPost(body, user, category);
        PostResponseDTO response = new PostResponseDTO(post);
        URI url = uriComponentsBuilder.path("/api/posts/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDTO>> listPosts(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(pageable).map(PostResponseDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping
    public ResponseEntity<PostResponseDTO> updatePost(@RequestBody @Valid UpdatePostDTO body){
        Category category = null;
        if (body.categoryId() != null) {
            category = categoryService.getCategoryById(body.categoryId());
        }
        return ResponseEntity.ok(postService.updatePost(body, category));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Page<PostResponseDTO>> postByCategory(@PageableDefault(size = 5) Pageable pageable,
                                                                @PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostsByCategory(id, pageable).map(PostResponseDTO::new));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<PostResponseDTO>> postByUser(@PageableDefault(size = 5) Pageable pageable,
                                                            @PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostsByUser(id, pageable).map(PostResponseDTO::new));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.apiSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token.substring(7));
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
