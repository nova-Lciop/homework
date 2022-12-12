package ssh.homework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.domain.Clazz;
import ssh.homework.service.ClazzService;
import ssh.homework.tag.PageModel;
//处理班级表tb_clazz的所有请求控制器
@Controller
public class ClazzController {
	//班级表tb_clazz的业务逻辑处理类ClazzService的实例，由Ioc容器注入
	@Autowired
	@Qualifier("clazzService")
	private ClazzService clazzService;
	//查询班级请求处理方法 
	@RequestMapping(value="/clazz/selectClazz")	
	 public String selectClazz(Integer pageIndex,
			 @ModelAttribute Clazz clazz,
			 Model model){
		//控制分页的对象
		PageModel pageModel = new PageModel();
		if(pageIndex != null){
			pageModel.setPageIndex(pageIndex);
		}
		/** 查询班级信息，clazzs存放满足查询条件的班级对象     */
		List<Clazz> clazzs = clazzService.findClazz(clazz, pageModel);
		/** clazzs存放在model中用，用于在JSP页面中显示     */	
		model.addAttribute("clazzs", clazzs);
		model.addAttribute("pageModel", pageModel);
		//转发到clazz目录下的clazz.jsp页面
		return "clazz/clazz";
		
	}
	/** 添加班级请求处理，flag＝1表示显示addClazz.jsp页面
	 * flag=2表示对tb_clazz表执行添加操作*/
	@RequestMapping(value="/clazz/addClazz")
	 public ModelAndView addClazz(
			 String flag,
			 @ModelAttribute Clazz clazz,
			 ModelAndView mv){
		if(flag.equals("1")){
			// 设置跳转到添加页面
			mv.setViewName("clazz/addClazz");
		}else{
			// 执行添加操作
			clazzService.addClazz(clazz);
			// 设置客户端跳转到查询班级请求
			mv.setViewName("redirect:/clazz/selectClazz");
		}
		// 返回
		return mv;
	}
	/** 删除班级请求处理方法，ids参数是一个字符串数组，存放的是要删除的班级id*/
	@RequestMapping(value="/clazz/removeClazz")
	 public ModelAndView removeClzz(String ids,ModelAndView mv){
		// 分解id字符串
		String[] idArray = ids.split(",");
		
		for(String id : idArray){
			// 根据id删除员工
			try {
		/**  根据id删除班级表tb_clazz中记录*/
			clazzService.removeClazzById(Integer.parseInt(id));
			}catch(Exception ec) {
				//如果有异常，将错误信息保存在error变量中
				mv.addObject("error","班级不可删除");
				//设置转发到error/error.jsp页面
				mv.setViewName("error/error");
				return mv;
			}
		}
		// 设置客户端跳转到查询请求
		mv.setViewName("redirect:/clazz/selectClazz");
		// 返回ModelAndView
		return mv;
	}
	/** 更新班级请求处理方法，clazz参数存放要更新的班级信息*/
	@RequestMapping(value="/clazz/updateClazz")
	 public ModelAndView updateClazz(
			 String flag,
			 @ModelAttribute Clazz clazz,
			 ModelAndView mv){
		//根据flag的值是1还是2决定是返回更新页面还是执行更新操作
		if(flag.equals("1")){
			// 根据id查询用户
			Clazz target = clazzService.findClazzById(clazz.getId());
			// 设置Model数据
			mv.addObject("clazz", target);
			// 返回修改员工页面
			mv.setViewName("clazz/updateClazz");
		}else{
			// 执行修改操作
			clazzService.modifyClazz(clazz);
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/clazz/selectClazz");
		}
		// 返回
		return mv;
	}

}
