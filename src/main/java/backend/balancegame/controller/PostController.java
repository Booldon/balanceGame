package backend.balancegame.controller;

import backend.balancegame.domain.Post;
import backend.balancegame.dto.PostDto;
import backend.balancegame.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @GetMapping("/list")
    @Operation(summary = "포스트 리스트", description = "전체 포스트 조회")
    public ResponseEntity<Page<Post>> postList(@RequestParam(value = "page",defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Post> newPost = postService.posts(pageable);

        return new ResponseEntity<>(newPost,HttpStatus.OK);
    }

    @GetMapping("{theme}")
    @Operation(summary = "테마 조회", description = "포스트 테마 조회")
    public ResponseEntity<Page<Post>> themeList(@PathVariable("theme") String theme,
                                                @RequestParam(value = "page",defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Post> themePostList = postService.themePosts(theme,pageable);


        return new ResponseEntity<>(themePostList,HttpStatus.OK);
    }
    @PostMapping("/new")
    @Operation(summary = "포스트 생성", description = "Dto를 통해 포스트 생성")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) {
        Post newPost = postService.create(postDto);

        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "포스트 검색", description = "제목으로 포스트 검색")
    public ResponseEntity<Page<Post>> searchPost(@RequestParam("title") String title,
                                                  @RequestParam(value = "page",defaultValue = "0") Integer page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Post> findPost = postService.search(title,pageable);

        return new ResponseEntity<>(findPost,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public HttpStatus deletePost(@RequestParam("id") Long id) {
        postService.delete(id);

        return HttpStatus.OK;
    }


    @PostConstruct
    private void init() {
        for(var i = 0; i<10; i++){
            Post post = Post.builder().title("title"+i).content("content"+i).theme("hello").build();
            postService.save(post);
        }
        for(var i = 0; i<10; i++){
            Post post = Post.builder().title("title"+i).content("content"+i).theme("bye").build();
            postService.save(post);
        }
        for(var i = 0; i<10; i++){
            Post post = Post.builder().title("title"+i).content("content"+i).theme("haha").build();
            postService.save(post);
        }
    }
}
