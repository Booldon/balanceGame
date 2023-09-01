package backend.balancegame.service;

import backend.balancegame.domain.Post;
import backend.balancegame.dto.PostDto;
import backend.balancegame.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> posts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage;
    }

    public Page<Post> themePosts(String theme, Pageable pageable) {
        Page<Post> themePostPage = postRepository.findByTheme(theme,pageable);
        return themePostPage;
    }
    public Post create(PostDto postDto) {
        Post newPost = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        postRepository.save(newPost);

        return newPost;
    }

    public void delete(Long postId) {
        Optional<Post> findPostOptional = postRepository.findById(postId);
        if(findPostOptional.isPresent()){
            Post findPost = findPostOptional.get();
            postRepository.delete(findPost);

        } else throw new RuntimeException("포스트를 찾을 수 없습니다.");
    }

    public Page<Post> search(String title, Pageable pageable) {
        Page<Post> seachPostPage = postRepository.findByTitle(title, pageable);
        return seachPostPage;
    }

    public void save(Post post) {
        postRepository.save(post);
    }

}
