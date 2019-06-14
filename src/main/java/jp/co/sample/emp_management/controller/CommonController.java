package jp.co.sample.emp_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 共通のコントローラ
 * 
 * @author momoyo kanie
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	@RequestMapping("/maintenance")
	public String maintenance() {
		return "error";
	}
	
}
