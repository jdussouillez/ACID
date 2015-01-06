package org.acid.controller;

import javax.ejb.EJB;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import org.acid.ejb.entities.User;
import org.acid.ejb.entitymanager.ACIDEntityManager;
import org.acid.ejb.logger.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    public static final int MIN_PW_LEN = 4;
    public static final int MAX_STR_LEN = 128;

    @EJB(mappedName = "logger")
    private Logger logger;

    @EJB(mappedName = "entityManager")
    private ACIDEntityManager entityManager;

    @RequestMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(HttpServletRequest request,
                                     @RequestParam(value = "inputUsername", required = true) String inputUsername,
                                     @RequestParam(value = "inputEmail", required = true) String inputEmail,
                                     @RequestParam(value = "inputPassword", required = true) String inputPassword,
                                     @RequestParam(value = "inputPasswordConfirmation", required = true) String inputPasswordConfirmation) {
        if (!inputPassword.equals(inputPasswordConfirmation)) {
            logger.debug("RegisterController", "Both passwords are not identical.");
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("inputUsername", inputUsername);
            mv.addObject("inputEmail", inputEmail);
            mv.addObject("errorMsg", "Both passwords are not identical");
            return mv;
        }
        if (inputUsername.length() > MAX_STR_LEN) {
            logger.debug("RegisterController", "Username is too long");
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("inputEmail", inputEmail);
            mv.addObject("errorMsg", "Your username is too long. It must be shorter than " + MAX_STR_LEN + " characters.");
            return mv;
        }
        if (inputEmail.length() > MAX_STR_LEN) {
            logger.debug("RegisterController", "Email address is too long");
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("inputUsername", inputUsername);
            mv.addObject("errorMsg", "Your email address is too long. It must be shorter than " + MAX_STR_LEN + " characters.");
            return mv;
        }
        if (inputPassword.length() > MAX_STR_LEN) {
            logger.debug("RegisterController", "Password is too long");
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("inputUsername", inputUsername);
            mv.addObject("inputEmail", inputEmail);
            mv.addObject("errorMsg", "Password is too long. It must be shorter than " + MAX_STR_LEN + " characters.");
            return mv;
        }

        if (entityManager.getUserByEmailAddress(inputEmail) != null) {
            logger.debug("RegisterController", "Email address already used");
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("inputUsername", inputUsername);
            mv.addObject("errorMsg", "Email address already used.");
            return mv;
        }

        User user = null;
        try {
            user = entityManager.createUser(inputEmail, inputUsername, inputPassword);
        } catch (PersistenceException ex) {
            logger.error("RegisterController", "Could not persist user", ex);
        }
        if (user == null) {
            logger.error("RegisterController", "Could not insert the user in the DB");
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("errorMsg", "An error occurred during registering.");
            return mv;
        }

        logger.info("RegisterController", "User " + user.getIdUser() + " '" + user.getName() + "' (" + user.getEmail() + ") created");
        // TODO
        return new ModelAndView("redirect:/");
    }
}
