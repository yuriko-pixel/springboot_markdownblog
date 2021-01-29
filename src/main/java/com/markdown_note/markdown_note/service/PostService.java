package com.markdown_note.markdown_note.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markdown_note.markdown_note.entity.Post;
import com.markdown_note.markdown_note.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public List<Post> getAllPosts() {
		List<Post> postList = new ArrayList<>();
    	Iterable<Post> iterable = postRepository.findAll();
    	iterable.forEach(postList::add);
    	return postList;
	}

	public void savePost(Post post) {
		postRepository.save(post);
	}

	public Post getPost(long id) {
		return postRepository.findOneById(id);
	}
}
