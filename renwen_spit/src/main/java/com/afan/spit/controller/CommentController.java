package com.afan.spit.controller;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.afan.spit.pojo.Comment;
import com.afan.spit.service.CommentService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * comment控制器层
 * @author afan
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping()
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",commentService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",commentService.findById(id));
	}
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/spit/{id}")
	public Result findByspit(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",commentService.findByspitId(id));
	}

	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@PostMapping("/search/{page}/{size}")
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Comment> pageList = commentService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Comment>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findSearch(searchMap));
    }

    @Autowired
  private JwtUtil jwtUtil;
	/**
	 * 增加
	 * @param
	 */
	@PostMapping()
	public Result add(@RequestBody Map<String,String> map  ){
		Comment comment = new Comment();
		String token = map.get("token");
		System.out.println(token);
		Claims claims = jwtUtil.parseJWT(token);//解析token

		String userid = claims.getId();
		String nickename = claims.getSubject();
		comment.setContent(map.get("content"));
		comment.setSpitid(map.get("parentid"));
		comment.setUserid(userid);
		comment.setNickname(nickename);
		commentService.add(comment);
		return new Result(true,StatusCode.OK,"评论成功");
	}
	
	/**
	 * 修改
	 * @param comment
	 */
	@PutMapping("/{id}")
	public Result update(@RequestBody Comment comment, @PathVariable String id ){
		comment.setId(id);
		commentService.update(comment);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id){
		commentService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
