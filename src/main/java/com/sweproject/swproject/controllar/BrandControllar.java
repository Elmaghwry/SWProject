package com.sweproject.swproject.controllar;

import com.sweproject.swproject.Entities.BrandEntity;
import com.sweproject.swproject.Entities.UserEntity;
import com.sweproject.swproject.Repositorys.BrandReopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BrandControllar {

    @Autowired
    private BrandReopsitory brandReopsitory ;
    @GetMapping("/AddBrand")
    public String AddBrandGet(Model model)
    {
        model.addAttribute("brandEntity",new BrandEntity());
        return "AddBrand";
    }
    @PostMapping("/AddBrand")
    public String AddBrandPost(Model model ,  HttpServletRequest request, @ModelAttribute BrandEntity brandEntity)
    {
        if(request.getSession().getId() == null)
        {
            System.out.println("no Sessions");
            return "redirect:/Login";
        }
        else {
            UserEntity temp = (UserEntity) request.getSession().getAttribute("user");
            if (temp == null) {
                System.out.println("temp is null ");
                return "redirect:/Login";
            } else if (temp.getType().equals("Admin")) {
                System.out.println("done");
                brandReopsitory.save(brandEntity);
                model.addAttribute("brandEntity",new BrandEntity());
                return "AddBrand";
            }
            else if (temp.getType().equals("StoreOwner"))
            {
                return "redirect:/HomeOwner";
            }
            else
            {
                return "redirect:/Home";
            }
        }

    }
}
