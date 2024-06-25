package com.example.feign.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class JsonPlaceholderService {

    @Autowired
    private JsonPlaceholderClient jsonPlaceholderClient;


//    public Page<Post> getAllPosts(PageRequest pageRequest) {
//        List<Post> allPosts = jsonPlaceholderClient.getAllPosts();
//        int total = allPosts.size();
//        int totalPages = (int) Math.ceil((double) total / pageRequest.getPageSize());
//
//        if (pageRequest.getPageNumber() >= totalPages) {
//            // If the requested page number is out of bounds, return an empty page
//            return Page.empty(pageRequest);
//        }
//
//        int start = (int) pageRequest.getOffset();
//        int end = Math.min((start + pageRequest.getPageSize()), total);
//        List<Post> paginatedPosts = allPosts.subList(start, end);
//        return new PageImpl<>(paginatedPosts, pageRequest, total);
//    }
@GetMapping("/posts")
List<Post> getAllPosts(){return jsonPlaceholderClient.getAllPosts();}

    public Post getPostById(Long id) {
        return jsonPlaceholderClient.getPostById(id);
    }

    public Post createPost(Post post) {
        return jsonPlaceholderClient.createPost(post);
    }
}
