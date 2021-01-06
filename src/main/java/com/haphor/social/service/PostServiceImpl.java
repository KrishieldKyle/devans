package com.haphor.social.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.PostDAO;
import com.haphor.social.dao.TechnologyDAO;
import com.haphor.social.dto.LikeDTO;
import com.haphor.social.dto.PostDTO;
import com.haphor.social.dto.TechnologyDTO;
import com.haphor.social.dto.request.AddOrUpdatePostRequestDTO;
import com.haphor.social.dto.request.AddTechnologiesToPostRequestDTO;
import com.haphor.social.dto.request.DeleteTechnologiesFromPostRequestDTO;
import com.haphor.social.dto.response.AllPostsResponseDTO;
import com.haphor.social.dto.response.DeletePostResponseDTO;
import com.haphor.social.dto.response.PostResponseDTO;
import com.haphor.social.dto.response.PostTechnologyResponseDTO;
import com.haphor.social.model.Post;
import com.haphor.social.model.Technology;
import com.haphor.social.model.User;
import com.haphor.social.util.converter.dto.ConvertDTO;
import com.haphor.social.util.converter.entity.ConvertEntity;
import com.haphor.social.util.jwt.AccessToken;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostDAO postDao;
	
	@Autowired
	private TechnologyDAO technologyDao;
	
	@Autowired
	private AccessToken accessToken;
	
	@Autowired
	private ConvertEntity convertEntity;
	
	@Autowired
	private ConvertDTO convertDTO;

	@Override
	public AllPostsResponseDTO getAllPost() {
		
		List<Post> posts = postDao.findAll();
		
		Set<LikeDTO> likesDto = new HashSet<>();
		
		Set<TechnologyDTO> technologiesDto = new HashSet<>();
		
		Set<PostDTO> postsDto = new HashSet<>();
		
		for(Post post : posts) {
			
			likesDto = convertEntity.toLikeDTOs(post.getLikes());
			
			technologiesDto = convertEntity.toTechnologyDTOs(post.getTechnologies());
			
			// Construct the post for response
			PostDTO postDto = new PostDTO(post.getPostId(), post.getUser().getUserId(), post.getSubject(), post.getContent(), 
					likesDto, technologiesDto, post.getCreatedAt(), post.getUpdatedAt());
			
			postsDto.add(postDto);
			
			likesDto = new HashSet<>();
			
			technologiesDto = new HashSet<>();
		}
		
		return new AllPostsResponseDTO(postsDto, true, "All posts have been fetched", HttpStatus.OK);
	}

	@Override
	public PostResponseDTO getPost(int postId) {
		
		Optional<Post> maybePost = postDao.findById(postId);
		
		if(maybePost.isPresent()) {
			
			Post post = maybePost.get();
			
			// Construct the likes for the post response
			Set<LikeDTO> likesDto = new HashSet<>();
			
			Set<TechnologyDTO> technologiesDto = new HashSet<>();
			
			likesDto = convertEntity.toLikeDTOs(post.getLikes());
			
			technologiesDto = convertEntity.toTechnologyDTOs(post.getTechnologies());
			
			// Construct the post for response
			PostDTO postDto = new PostDTO(post.getPostId(), post.getUser().getUserId(), post.getSubject(), post.getContent(), 
					likesDto, technologiesDto, post.getCreatedAt(), post.getUpdatedAt());
			
			return new PostResponseDTO(postDto, true, "Post successfully fetched", HttpStatus.OK);
			
		}
		
		return new PostResponseDTO(new PostDTO(), false, "Post not found.", HttpStatus.NOT_FOUND);
	}

	@Override
	public PostResponseDTO saveOrUpdatePost(AddOrUpdatePostRequestDTO addOrUpdatePostRequestDTO) {
		
		User user = accessToken.getUserFromToken();
		
		if(user.getUserId() != addOrUpdatePostRequestDTO.getUserId()) {
			return new PostResponseDTO(new PostDTO(), false, "User ID does not match the currently logged in user", HttpStatus.UNAUTHORIZED);
		}
		
		Optional<Post> maybePost = postDao.findById(addOrUpdatePostRequestDTO.getPostId());
		
		// Check if there is an existing post with the given id
		if(maybePost.isPresent()) {
			
			Post post = maybePost.get();
			
			// Check if the post belongs to the currently logged in user
			if(post.getUser().getUserId() != user.getUserId()) {
				return new PostResponseDTO(new PostDTO(), false, "You are not authorized to update this post", HttpStatus.UNAUTHORIZED);
			}
			
			// Update the post
			post.setSubject(addOrUpdatePostRequestDTO.getSubject());
			post.setContent(addOrUpdatePostRequestDTO.getContent());
			
			Set<Technology> currentTechnologies = post.getTechnologies();
			
			Set<Technology> newTechnologies = convertDTO.toTechnologyEntities(addOrUpdatePostRequestDTO.getTechnologies());
			
			technologyDao.saveAll(newTechnologies);
			
			currentTechnologies.addAll(newTechnologies);
			
			post.setTechnologies(currentTechnologies);
			
			// Persist the post
			Post newPost = postDao.save(post);
			
			Set<LikeDTO> likesDto = convertEntity.toLikeDTOs(newPost.getLikes());
			
			Set<TechnologyDTO> technologiesDto = convertEntity.toTechnologyDTOs(newPost.getTechnologies());
			
			PostDTO postDto = new PostDTO(newPost.getPostId(), newPost.getUser().getUserId(), newPost.getSubject(), newPost.getContent(),
					likesDto, technologiesDto, newPost.getCreatedAt(), newPost.getUpdatedAt());
			
			return new PostResponseDTO(postDto, true, "Post has been updated.", HttpStatus.OK);
		}
		
		// New post
		
		Set<Technology> newTechnologies = convertDTO.toTechnologyEntities(addOrUpdatePostRequestDTO.getTechnologies());
		
		technologyDao.saveAll(newTechnologies);
		
		Post post = new Post();
		
		post.setSubject(addOrUpdatePostRequestDTO.getSubject());
		post.setContent(addOrUpdatePostRequestDTO.getContent());
		post.setTechnologies(newTechnologies);
		post.setUser(user);
		
		Post newPost = postDao.save(post);
		
		Set<TechnologyDTO> technologiesDto = convertEntity.toTechnologyDTOs(newPost.getTechnologies());
		
		PostDTO postDto = new PostDTO(newPost.getPostId(), newPost.getUser().getUserId(), newPost.getSubject(),
				newPost.getContent(), new HashSet<>(), technologiesDto, newPost.getCreatedAt(), newPost.getUpdatedAt());
		
		return new PostResponseDTO(postDto, true, "Post has been created.", HttpStatus.OK);
	}

	@Override
	public DeletePostResponseDTO deletePost(int postId) {
		
		User user = accessToken.getUserFromToken();
		
		Optional<Post> post = postDao.findById(postId);
		
		if(post.isPresent() && post.get().getUser().getUserId() == user.getUserId()) {
			postDao.deleteById(postId);
			return new DeletePostResponseDTO(postId, true, "Post has been deleted.", HttpStatus.OK);
		}
		
		return new DeletePostResponseDTO(postId, false, "You are not authorized to delete this post", HttpStatus.UNAUTHORIZED);
		
	}
	
	@Override
	public PostTechnologyResponseDTO addTechnologiesToPost(AddTechnologiesToPostRequestDTO addTechnologyToPostRequestDTO) {
		
		User user = accessToken.getUserFromToken();
		
		if(user.getUserId() != addTechnologyToPostRequestDTO.getUserId()) {
			return new PostTechnologyResponseDTO(false, "User ID does not match the currently logged in user", 
					addTechnologyToPostRequestDTO.getPostId(), new HashSet<>(), HttpStatus.UNAUTHORIZED);
		}
		
		if(addTechnologyToPostRequestDTO.getTechnologies().isEmpty()) {
			return new PostTechnologyResponseDTO(true, "No technology has been added", 
					addTechnologyToPostRequestDTO.getPostId(), new HashSet<>(), HttpStatus.OK);
		}
		
		Optional<Post> maybePost = postDao.findById(addTechnologyToPostRequestDTO.getPostId());
		
		if(maybePost.isPresent() && maybePost.get().getUser().getUserId() == user.getUserId()) {
			
			Post post = maybePost.get();
			
			Set<Technology> newTechnologies = convertDTO.toTechnologyEntities(addTechnologyToPostRequestDTO.getTechnologies());
			
			newTechnologies = new HashSet<>(technologyDao.saveAll(newTechnologies));
			
			Set<Technology> currentTechnologies = post.getTechnologies();
			
			currentTechnologies.addAll(newTechnologies);
			
			post.setTechnologies(currentTechnologies);
			
			Post newPost = postDao.save(post);
			
			Set<TechnologyDTO> technologiesDto = convertEntity.toTechnologyDTOs(newPost.getTechnologies());
			
			return new PostTechnologyResponseDTO(true, "Successfully added technologies to the post", 
					addTechnologyToPostRequestDTO.getPostId(), technologiesDto, HttpStatus.OK);
		}
		
		return new PostTechnologyResponseDTO(false, "You are not authorized to add technologies to this post.", 
				addTechnologyToPostRequestDTO.getPostId(), new HashSet<>(), HttpStatus.UNAUTHORIZED);

	}

	@Override
	public PostTechnologyResponseDTO deleteTechnologiesFromPost(DeleteTechnologiesFromPostRequestDTO deleteTechnologiesFromPostRequestDTO) {
		
		User user = accessToken.getUserFromToken();
		
		if(user.getUserId() != deleteTechnologiesFromPostRequestDTO.getUserId()) {
			return new PostTechnologyResponseDTO(false, "User ID does not match the currently logged in user", 
					deleteTechnologiesFromPostRequestDTO.getPostId(), new HashSet<>(), HttpStatus.UNAUTHORIZED);
		}
		
		if(deleteTechnologiesFromPostRequestDTO.getTechnologies().isEmpty()) {
			return new PostTechnologyResponseDTO(true, "No technology has been deleted", 
					deleteTechnologiesFromPostRequestDTO.getPostId(), new HashSet<>(), HttpStatus.OK);
		}
		
		Optional<Post> maybePost = postDao.findById(deleteTechnologiesFromPostRequestDTO.getPostId());
		
		if(maybePost.isPresent() && maybePost.get().getUser().getUserId() == user.getUserId()) {
			
			Post post = maybePost.get();
			
			Set<Technology> newTechnologies = post.getTechnologies();
			
			for(TechnologyDTO technologyDto : deleteTechnologiesFromPostRequestDTO.getTechnologies()) {
				
				for(Technology postTechnology : post.getTechnologies()) {
					if(technologyDto.getTechnologyId() == postTechnology.getTechnologyId()) {
						newTechnologies.remove(postTechnology);
						break;
					}
				}
				
			}
			
			post.setTechnologies(newTechnologies);
			
			Post newPost = postDao.save(post);
			
			Set<TechnologyDTO> technologiesDto = convertEntity.toTechnologyDTOs(newPost.getTechnologies());

			return new PostTechnologyResponseDTO(true, "Technologies have been added.", 
					deleteTechnologiesFromPostRequestDTO.getPostId(), technologiesDto, HttpStatus.OK);
		}
		
		return new PostTechnologyResponseDTO(false, "You are not authorized to delete a technology from this post", 
				deleteTechnologiesFromPostRequestDTO.getPostId(), new HashSet<>(), HttpStatus.UNAUTHORIZED);
	}
}
