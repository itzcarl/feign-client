package com.example.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "jsonplaceholder", url = "https://jsonplaceholder.typicode.com")
public interface JsonPlaceholderClient {

    @GetMapping("/posts")
    List<Post> getAllPosts();


    @GetMapping("/posts/{id}")
    Post getPostById(@PathVariable("id") Long id);

    @PostMapping("/posts")
    Post createPost(@RequestBody Post post);
}
