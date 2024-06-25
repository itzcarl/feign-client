package com.example.feign.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private JsonPlaceholderService jsonPlaceholderService;

//    @GetMapping
//    public Page<Post> getAllPosts(@RequestParam(defaultValue = "0") @Min(0) int page,
//                                  @RequestParam(defaultValue = "10") @Min(1) int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return jsonPlaceholderService.getAllPosts(pageRequest);
//    }

    @GetMapping
    public List<Post> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        // Fetch all posts
        List<Post> allPosts = jsonPlaceholderService.getAllPosts();

        // Convert to a mutable list
        List<Post> mutablePosts = new ArrayList<>(allPosts);

        // Client-side filtering
        if (filter != null && !filter.isEmpty()) {
            mutablePosts = mutablePosts.stream()
                    .filter(post -> post.getTitle().toLowerCase().contains(filter.toLowerCase()) ||
                            post.getBody().toLowerCase().contains(filter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Client-side sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            mutablePosts.sort((post1, post2) -> {
                int comparison = 0;
                if ("title".equals(sortBy)) {
                    comparison = post1.getTitle().compareToIgnoreCase(post2.getTitle());
                } else if ("body".equals(sortBy)) {
                    comparison = post1.getBody().compareToIgnoreCase(post2.getBody());
                } else if ("id".equals(sortBy)) {
                    comparison = post1.getId().compareTo(post2.getId());
                }
                return "desc".equals(sortOrder) ? -comparison : comparison;
            });
        }

        // Client-side pagination
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, mutablePosts.size());
        return mutablePosts.subList(fromIndex, toIndex);
    }



    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return jsonPlaceholderService.getPostById(id);
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return jsonPlaceholderService.createPost(post);
    }
}
