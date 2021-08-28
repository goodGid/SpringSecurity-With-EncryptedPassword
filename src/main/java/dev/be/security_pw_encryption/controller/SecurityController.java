package dev.be.security_pw_encryption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/encrypt_pw")
    public String securityPassword(@RequestParam("pw") String pw) {
        return passwordEncoder.encode(pw);
    }

}
