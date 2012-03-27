package com.googlecode.qualitas.webapp.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class SecurityController.
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    /**
     * Login.
     * 
     * @return the string
     */
    @RequestMapping(value = "/login")
    public String login() {
        return "authentication/login";
    }

}
