package com.healthmate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthmate.model.BlogPost;
import com.healthmate.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogPostRepository extends JpaRepository<BlogPost, String> {
    List<BlogPost> findByStatus(PostStatus status);

    List<BlogPost> findByAuthorId(String authorId);

    List<BlogPost> findByCategory(String category);

    List<BlogPost> findByStatusOrderByCreatedAtDesc(PostStatus status);

    Page<BlogPost> findByStatusOrderByCreatedAtDesc(PostStatus status, Pageable pageable);
}
