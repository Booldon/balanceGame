package backend.balancegame.repository;

import backend.balancegame.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPageById(Long id, PageRequest pageRequest);
    Page<Post> findByTitle(String title, Pageable pageable);
    Page<Post> findByTheme(String Theme, Pageable pageable);

}
