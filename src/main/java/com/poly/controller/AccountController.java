package com.poly.controller;

import com.poly.model.Account;
import com.poly.request.AccountRequest;
import com.poly.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/admin")
public class AccountController {

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;
    

    @GetMapping("/user")
    public ResponseEntity<?> getAllUser() {
        List<Account> accounts = accountService.findAll();

        System.out.println(accounts);

        return new ResponseEntity<>(accounts, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@RequestParam("id") String id) {
        System.out.println(id);
        accountService.delete(id);
        return null;
    }

    @PostMapping("/users")
    public ResponseEntity<?> updateAccount(@RequestBody AccountRequest accountRequest) {
        Optional<Account> account = accountService.findById(accountRequest.getUserName());
        System.out.println(accountRequest);
        System.out.println("Đã vào");
        if(account.isPresent()) {

            Account accountNew = new Account();
            accountNew.setPassword(passwordEncoder.encode(account.get().getPassword()));
            accountNew.setUsername(accountRequest.getUserName());
            accountNew.setFullname(accountRequest.getFullname());
            accountNew.setEmail(accountRequest.getEmail());
            accountNew.setPhoto(account.get().getPhoto());
            accountNew.setToken(account.get().getToken());
            accountNew.setRoles(account.get().getRoles());
            accountNew.setActivated(accountRequest.getActivated());

            Account acc = accountService.update(accountNew);

            return new ResponseEntity<>(acc, HttpStatus.ACCEPTED);

        }

        Account createAcc = new Account();

        createAcc.setUsername(accountRequest.getUserName());
        createAcc.setFullname(accountRequest.getFullname());
        createAcc.setEmail(accountRequest.getEmail());
        Account acc = accountService.create(createAcc);
        return new ResponseEntity<>(acc, HttpStatus.ACCEPTED);
    }


}
