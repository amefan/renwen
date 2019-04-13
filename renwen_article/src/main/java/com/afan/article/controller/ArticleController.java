package com.afan.article.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.afan.article.pojo.Article;
import com.afan.article.service.ArticleService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.multipart.MultipartFile;

/**
 * article控制器层
 * @author afan
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping()
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",articleService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",articleService.findById(id));
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
		Page<Article> pageList = articleService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param article
	 */
	@PostMapping()
	public Result add(@RequestBody Article article  ){
		articleService.add(article);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@PutMapping("/{id}")
	public Result update(@RequestBody Article article, @PathVariable String id ){
		article.setId(id);
		articleService.update(article);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id){
		articleService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	/**
	 * @Description: 上传logo
	 * @author: afan
	 * @param:
	 * @return:
	 */
	@PostMapping("/uploadlogo")
	public Result upload(MultipartFile file){
		String UPLOAD_FOLDER = "E:/upload/logo/";

		if (file.isEmpty()) {
			return new Result(false,StatusCode.ERROR,"上转文件为空");
		}

		try {
			String subfix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("" +
					"."));
			String newName = "logo_"+System.currentTimeMillis()+subfix;
			//System.out.println(newName);
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_FOLDER + newName);
			//如果没有files文件夹，则创建
			if (!Files.isWritable(path)) {
				Files.createDirectories(Paths.get(UPLOAD_FOLDER));
			}
			//文件写入指定路径
			Files.write(path, bytes);
			String url = "http://localhost/logo/"+newName;
			return new Result(true,StatusCode.OK,"成功",url);
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(false, StatusCode.ERROR, "后端异常");
		}
	}
	/**
	 * @Description: 审核
	 * @author: afan
	 * @param: [id]
	 * @return: entity.Result
	 */
	@PutMapping("/examine/{id}")
	public Result examine(@PathVariable String id){
		System.out.println(id);
		articleService.examine(id);
		return new Result(true, StatusCode.OK, "审核成功");
	}

}
