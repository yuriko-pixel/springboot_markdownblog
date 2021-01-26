package com.markdown_note.markdown_note.controller;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.markdown_note.markdown_note.entity.Post;
import com.markdown_note.markdown_note.service.PostService;

@Controller
public class IndexController {

	@Autowired
	private PostService postService;

	@GetMapping
	public String main(Model model) {
		model.addAttribute("post", new Post());
		return "index";
	}

	@PostMapping
	public String save(Post post, Model model) {
		post.setHtml(markdownToHTML(post.getContent()));
		post.setId((long) 1);
		postService.savePost(post);
		System.out.println(postService.getAllPosts());
		model.addAttribute("postList", postService.getAllPosts());
		return "saved";
	}

	private String markdownToHTML(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();

		return renderer.render(document);
	}
}