package com.sweproject.swproject.controllar;

import com.sweproject.swproject.Entities.Cart;
import com.sweproject.swproject.Entities.UserEntity;
import com.sweproject.swproject.Repositorys.CartReopsitory;
import com.sweproject.swproject.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Controller
public class User {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartReopsitory cartReopsitory;

    @GetMapping("/Regester")
    public String RegesterGet(Model model)
    {
        model.addAttribute("userEntity",new UserEntity());
        return "RegesterForm";
    }

    @PostMapping("/Regester")
    public String RegesterPost(Model model , @ModelAttribute UserEntity userEntity)
    {
        Iterable<UserEntity> getUsers = userRepository.findAll();
        boolean check = true ;
        for(UserEntity userEntity1 : getUsers)
        {
            if(userEntity.getEmail()==userEntity1.getEmail())
            {
                check=false;
                break;
            }
        }
        if(check==true) {
            userRepository.save(userEntity);
            model.addAttribute("userEntity", new UserEntity());
        }
        return "RegesterForm";
    }


    @GetMapping("/Login")
    public String LoginGet(Model model)
    {
        model.addAttribute("userEntity",new UserEntity());
        return "LoginForm";
    }
    public int getId(String email)
    {
        Iterable<UserEntity> getUsers = userRepository.findAll();
        for (UserEntity userEntity : getUsers ) {
            if(userEntity.getEmail().equals(email))
            {
                return userEntity.getId();
            }
        }
        return -1;
    }
    @PostMapping("/Login")
    public String LoginPost(Model model , @ModelAttribute UserEntity userEntity,  HttpServletRequest request)
    {
        int ID = getId(userEntity.getEmail());
        if(ID==-1)
        {
            System.out.println("error in email");
        }
        else
        {
            Optional<UserEntity> getUser = userRepository.findById(ID);
            String EmailUser = getUser.get().getEmail() , PassUser = getUser.get().getPassword() ,
                    UserType = getUser.get().getType() ,
                    EmaliTemp = userEntity.getEmail() , PassTemp = userEntity.getPassword() ;
            if(PassTemp.equals(PassUser))
            {
                if (UserType.equals("Normal"))
                {
                    System.out.println("user");
                    request.getSession().setAttribute("user", getUser.get());
                    System.out.println("ID "+request.getSession().getId());
                    return "redirect:/Home";
                }
                else if (UserType.equals("StoreOwner"))
                {
                    request.getSession().setAttribute("user", getUser.get());
                    return "redirect:/HomeOwner";
                }
                else if (UserType.equals("Admin"))
                {
                    request.getSession().setAttribute("user", getUser.get());
                    return "redirect:/HomeAdmin";
                }
            }

        }
        model.addAttribute("userEntity",new UserEntity());
        return "LoginForm";
    }
    public Vector<Cart> getCartForOwner(String email)
    {
        Vector<Cart> myCarts = new Vector<Cart>();
        Iterable<Cart> carts = cartReopsitory.findAll();
        for (Cart cart : carts ) {
            if(cart.getOwner().equals(email))
            {
                myCarts.add(cart);
            }
        }
        return  myCarts ;
    }

    @GetMapping("/LogOut")
    public String LogOut(HttpServletRequest request)
    {
        if(request.getSession().getId() == null)
        {
            System.out.println("no Sessions");
        }
        else {
            UserEntity temp = (UserEntity) request.getSession().getAttribute("user");
            if (temp == null) {
                System.out.println("temp is null ");
            } else {
                System.out.println("done");
                Vector<Cart> myCarts = getCartForOwner(temp.getEmail());
                for (int i =0;i<myCarts.size();i++)
                {
                    cartReopsitory.delete(myCarts.get(i));
                }
            }
        }
        return "redirect:/Login";
    }


    @GetMapping("/Home")
    public String Home(HttpServletRequest request)
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
            } else if (temp.getType().equals("Normal")) {
                System.out.println("done");
                return "home";
            }
            else if (temp.getType().equals("Admin"))
            {
                return "redirect:/HomeAdmin";
            }
            else
            {
                return "redirect:/HomeOwner";
            }
        }
    }

    @GetMapping("/HomeOwner")
    public String HomeOwner(HttpServletRequest request)
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
            } else if (temp.getType().equals("StoreOwner")){
                System.out.println("done");
                return "homeOwner" ;
            }
            else if (temp.getType().equals("Admin"))
            {
                return "redirect:/HomeAdmin";
            }
            else
            {
                return "redirect:/Home";
            }
        }
    }

    @GetMapping("/HomeAdmin")
    public String HomeAdmin(HttpServletRequest request)
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
            } else if (temp.getType().equals("Admin"))  {
                //System.out.println("done");
                return "homeAdmin" ;
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

    /*@GetMapping("/AddCollaborators")
    public String AddCollaborators(Model model , @ModelAttribute UserEntity userEntity ,  HttpServletRequest request  )
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
            } else if (temp.getType().equals("StoreOwner")) {
                System.out.println("done");
                int ID = getId(userEntity.getEmail());
                System.out.println(userEntity.getEmail()+"  "+ID);
                Optional<UserEntity> user = userRepository.findById(ID);
                user.get().setCollaborators(1);
                System.out.println(user.get().getEmail()+"  "+user.get().getPassword()+" "+user.get().getCollaborators());
                userRepository.save(user.get());
                model.addAttribute("userEntity", new UserEntity());
                return "AddCollaborators";
            }
            else if (temp.getType().equals("Admin"))
            {
                return "redirect:/HomeAdmin";
            }
            else
            {
                return "redirect:/Home";
            }
        }
    }*/
    @GetMapping("/MaxU")
    public String MaxUser(Model model ,@ModelAttribute UserEntity userEntity ,  HttpServletRequest request)
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
                Iterable<UserEntity> getUsers = userRepository.findAll();
                List<UserEntity> UserList = new ArrayList<UserEntity>();
                for(UserEntity user :getUsers )
                {
                    UserList.add(user);
                }
                UserEntity user = UserList.get(0);
                for(int i =1; i<UserList.size();i++)
                {
                    if(UserList.get(i).getNumOfBuy()>user.getNumOfBuy())
                    {
                        user = UserList.get(i);
                    }
                }
                System.out.println(user.getName() + " "+user.getNumOfBuy());
                model.addAttribute("user",user);
                return "StaticUser";
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

    @GetMapping("/MinU")
    public String MinUser(Model model,@ModelAttribute UserEntity userEntity,  HttpServletRequest request )
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
                Iterable<UserEntity> getUsers = userRepository.findAll();
                List<UserEntity> UserList = new ArrayList<UserEntity>();
                for(UserEntity user :getUsers )
                {
                    UserList.add(user);
                }
                UserEntity user = UserList.get(0);
                for(int i =1; i<UserList.size();i++)
                {
                    if(UserList.get(i).getNumOfBuy()<user.getNumOfBuy())
                    {
                        user = UserList.get(i);
                    }
                }
                System.out.println(user.getName() + " "+user.getNumOfBuy());
                model.addAttribute("user",user);
                return "StaticUser" ;
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
