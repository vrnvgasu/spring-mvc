//package ru.springmvc.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.springmvc.dao.PersonDAO;
//
//@Controller
//@RequestMapping("test-batch-update")
//public class BatchController {
//
//	private final PersonDAO personDAO;
//
//	@Autowired
//	public BatchController(PersonDAO personDAO) {
//		this.personDAO = personDAO;
//	}
//
//	@GetMapping()
//	public String index() {
//		return "batch/index";
//	}
//
//	@GetMapping("/without")
//	public String withoutBatch() {
//		// тестирует вставку отдельными записями
//		personDAO.testMultipleUpdate();
//		return "redirect:/people";
//	}
//
//	@GetMapping("/with")
//	public String withBatch() {
//		// тестирует вставку отдельными записями
//		personDAO.testBatchUpdate();
//		return "redirect:/people";
//	}
//}
