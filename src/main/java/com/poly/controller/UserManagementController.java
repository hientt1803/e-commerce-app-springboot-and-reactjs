package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Account;
import com.poly.service.ParamService;
import com.poly.service.impl.AccountServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/account")
public class UserManagementController {


    @Autowired
	AccountServiceImpl AccountService;
	@Autowired
	ParamService paramService;

	@GetMapping("")
	@ResponseBody
	public List<Account> getAll() {
		return AccountService.findAll();
	}

	// @PutMapping("")
	// @ResponseBody
	// public ResponseEntity<Account> update(@RequestBody Account Account) {

	// 	System.out.println("CREATE DATE" + Account.getCreatedDate());
	// 	return ResponseEntity.ok(AccountService.save(Account));
	// }

	// @PostMapping("")
	// @ResponseBody
	// public ResponseEntity<Account> add(@RequestBody Account Account) {

	// 	System.out.println("CREATE DATE" + Account.getCreatedDate());
	// 	return ResponseEntity.ok(AccountService.save(Account));
	// }







    // @Autowired
    // AccountDAO accountDAO;
    // @Autowired
    // SessionService sessionService;

    // @Autowired
    // ParamService paramService;

    // /*
    //  * @param: field: field to sort; eop: element of page; p: page to display; d:
    //  * direction(true is default(asending) / false is desending )
    //  */
    // @GetMapping("user")
    // public String userManagementView(Model model, @RequestParam("field") Optional<String> field,
    //         @RequestParam("eop") Optional<Integer> eop,
    //         @RequestParam("showAdmin") Optional<Boolean> showAdmin,
    //         @RequestParam("p") Optional<Integer> p, @RequestParam("d") Optional<Boolean> direc) {
    //     model.addAttribute("isPageActive", "user");

    //     int defaultPage = 0;
    //     int defaultElementOfPage = 5;
    //     String defaultField = "username";
    //     String keyword = paramService.getString("keyword", " ");
    //     if (sessionService.get("isUpdated") != null) {
    //         model.addAttribute("isUpdated", true);
    //         sessionService.remove("isUpdated");
    //     }
    //     // asending is default
    //     Pageable pageable = PageRequest.of(p.orElse(defaultPage), eop.orElse(defaultElementOfPage),
    //             Sort.by(field.orElse(defaultField)).ascending());

    //     if (direc.isPresent() && !direc.get().booleanValue()) {
    //         pageable = PageRequest.of(p.orElse(defaultPage), eop.orElse(defaultElementOfPage),
    //                 Sort.by(field.orElse(defaultField)).descending());

    //     }

    //     Page<Account> page = accountDAO.findByFullnameLike("%" + keyword + "%", pageable);
    //     System.out.println(page.getContent().size() + " size");

    //     if (page.isEmpty()) {
    //         model.addAttribute("isPageEmpty", true);
    //     } else {
    //         model.addAttribute("isPageEmpty", false);
    //         model.addAttribute("page", page);
    //     }
    //     Account account = sessionService.get("account");
    //     model.addAttribute("username", account.getUsername());
    //     model.addAttribute("keyword", keyword);
    //     model.addAttribute("field", field.orElse(defaultField));
    //     model.addAttribute("eop", eop.orElse(defaultElementOfPage));
    //     model.addAttribute("p", p.orElse(defaultPage));
    //     model.addAttribute("d", direc.orElse(true));
    //     model.addAttribute("users", accountDAO.findAll());
    //     model.addAttribute("title", "QUẢN LÝ NGƯỜI DÙNG");
    //     model.addAttribute("pageActive", "user");
    //     return "/admin/user";
    // }

    // @PostMapping("/user/delete/{id}")
    // public String disableUser(@PathVariable("id") String id) {
    //     Account account = accountDAO.findById(id).get();
    //     // account.setActivated(false);
    //     accountDAO.save(account);
    //     sessionService.set("isUpdated", true);
    //     return "redirect:/admin/user";
    // }

    // @PostMapping("/user/restore/{id}")
    // public String restoreUser(@PathVariable("id") String id) {
    //     Account account = accountDAO.findById(id).get();
    //     // account.setActivated(true);
    //     accountDAO.save(account);
    //     sessionService.set("isUpdated", true);
    //     return "redirect:/admin/user";
    // }

    // @RequestMapping("search")
    // public String searchUser(@RequestParam("keyword") String keyword) {

    //     return "forward:/admin/user";
    // }
}
