package ru.job4j.dreamjob.controller;

import net.jcip.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.model.*;

import javax.servlet.http.*;

@ThreadSafe
@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "index";
    }

}