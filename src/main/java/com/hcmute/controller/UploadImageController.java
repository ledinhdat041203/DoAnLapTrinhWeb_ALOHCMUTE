package com.hcmute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hcmute.Entity.ImageEntity;
import com.hcmute.Service.ImageService;

@Controller
public class UploadImageController {
    private final ImageService imageService;

    @Autowired
    public UploadImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/uploadImage")
    public String uploadImage(Model model) {
    	//ImageEntity imageEntity = new ImageEntity();
    	//model.addAttribute("imageEntity", imageEntity)
        return "uploadImage";
    }

    @PostMapping("/postImage")
    public String processImage(@RequestBody String file) {
    	ImageEntity imageEntity = new ImageEntity(file);
    	System.out.println("path file: "+ file);
        imageService.save(imageEntity);
        return "redirect:/uploadImage";
    }
}

