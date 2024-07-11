package com.mthor.blogchallenge.domain.service.implement;

import com.mthor.blogchallenge.domain.dto.post.CreatePostDTO;
import com.mthor.blogchallenge.domain.dto.post.PostResponseDTO;
import com.mthor.blogchallenge.domain.dto.post.UpdatePostDTO;
import com.mthor.blogchallenge.domain.entity.Category;
import com.mthor.blogchallenge.domain.entity.Post;
import com.mthor.blogchallenge.domain.entity.User;
import com.mthor.blogchallenge.domain.service.IPostService;
import com.mthor.blogchallenge.infra.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findByActiveTrue(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDTO getPostById(Long id) {
        Optional<Post> post = postRepository.findByActiveTrueAndId(id);
        if(post.isPresent()){
            return new PostResponseDTO(post.get());
        }
        throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public Post createPost(CreatePostDTO postDTO, User user, Category category) {
        Post post = new Post();
        post.setTitle(postDTO.title());
        post.setContent(postDTO.content());
        post.setUser(user);
        post.setCategory(category);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public PostResponseDTO updatePost(UpdatePostDTO updatePostDTO, Category category) {
        Post post = postRepository.getReferenceById(updatePostDTO.id());
        post.updateData(updatePostDTO, category);
        return new PostResponseDTO(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getPostsByCategory(Long categoryId, Pageable pageable) {
        return postRepository.findByCategory_Id(categoryId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getPostsByUser(Long userId, Pageable pageable) {
        return postRepository.findByUser_Id(userId, pageable);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.getReferenceById(id);
        post.changeState();
    }
}
