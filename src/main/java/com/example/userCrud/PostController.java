package com.example.userCrud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult; // ✅ ADDED: Needed to capture validation errors
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // GET all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // GET post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE post for a user
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createPost(
            @PathVariable Long userId,               // Extracts the user ID from the URL path
            @Valid @RequestBody PostDTO postDTO,     // Validates the incoming JSON body against PostDTO constraints
            BindingResult bindingResult) {           // ✅ ADDED: Captures validation errors, if any

        // If there are validation errors, return a 400 Bad Request with error details
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST); // ✅ ADDED
        }

        // Find the user in the database by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Post object and populate it from the PostDTO
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(user); // Associate the post with the found user

        // Save the new post to the database and return a 201 Created response
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.CREATED);
    }

    // UPDATE post
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestBody Post updatedPost) {
        Post existingPost = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());

        return ResponseEntity.ok(postRepository.save(existingPost));
    }

    // DELETE post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
