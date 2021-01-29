package com.markdown_note.markdown_note.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.markdown_note.markdown_note.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{
	public Post findOneById(long id);
}
