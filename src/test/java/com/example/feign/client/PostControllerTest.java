package com.example.feign.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JsonPlaceholderService jsonPlaceholderService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Post> postList;

    @BeforeEach
    public void setup() {
        Post post1 = new Post(1L, 1L, "Post 1", "Post 1 Body");
        Post post2 = new Post(2L, 1L, "Post 2", "Post 2 Body");
        postList = Arrays.asList(post1, post2);

        Mockito.when(jsonPlaceholderService.getAllPosts()).thenReturn(postList);
        Mockito.when(jsonPlaceholderService.getPostById(anyLong())).thenReturn(post1);
        Mockito.when(jsonPlaceholderService.createPost(any(Post.class))).thenReturn(post1);
    }

    @Test
    public void testGetAllPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postList)));
    }

    @Test
    public void testGetPostById() throws Exception {
        mockMvc.perform(get("/posts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postList.get(0))));
    }

    @Test
    public void testCreatePost() throws Exception {
        Post newPost = new Post(null, 1L, "New Post", "New Post Body");
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPost)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postList.get(0))));
    }
}
