package com.markdown_note.markdown_note.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.markdown_note.markdown_note.entity.Post;
import com.markdown_note.markdown_note.service.PostService;

@Controller
@RequestMapping("/")
public class IndexController {
	@Bean
	public Java8TimeDialect java8TimeDialect() {
	    return new Java8TimeDialect();
	}

	@Autowired
	private PostService postService;

	@GetMapping("/blogtop")
	public String getBlogtop(Model model) {
		List<Post> postList = postService.getAllPosts();
		model.addAttribute("postList", postList);
		return "blogtop";
	}

	@GetMapping("/saved/{postId}")
	public String getSaved(@PathVariable("postId") long id,Model model) {
		Post post2 = postService.getPost(id);
		model.addAttribute("post", post2);

		model.addAttribute("postdate", setDateFormat(post2));
		return "saved";
	}

	@GetMapping
	public String main(Model model) {
		model.addAttribute("post", new Post());
		return "index";
	}

	@PostMapping
	public String save(Post post, Model model) {
		post.setHtml(markdownToHTML(post.getContent().replaceAll("\r","<br/>")));
		post.setTitle("<h1>"+post.getTitle()+"</h1>");
		post.setId((long) 1);
		postService.savePost(post);
		List<Post> postList = postService.getAllPosts();
		for(Post p: postList) {
			System.out.println(p.getTag());
		}

		model.addAttribute("postList", postList);
		return "saved";
	}

	private String markdownToHTML(String markdown) {
//		String md = markdown.replaceAll("\r","<br/>");
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
//		HtmlRenderer renderer = HtmlRenderer.builder().softbreak("<br/>").build();
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}

	public String setDateFormat(Post post) {
	    SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd日 E H:mm");
	    return format2.format(post.getPostdate());
	}
}