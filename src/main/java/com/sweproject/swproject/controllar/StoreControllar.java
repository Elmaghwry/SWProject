package com.sweproject.swproject.controllar;

import com.sweproject.swproject.Entities.StoreEntity;
import com.sweproject.swproject.Entities.UserEntity;
import com.sweproject.swproject.Entities.WaitingStores;
import com.sweproject.swproject.Repositorys.StoreReopsitory;
import com.sweproject.swproject.Repositorys.WaitingStoresReopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class StoreControllar {

    @Autowired
    private StoreReopsitory storeReopsitory ;
    @Autowired
    private WaitingStoresReopsitory waitingStoresReopsitory ;
    @GetMapping("/ShowStores")
    public String ShowStores(Model model ,  HttpServletRequest request )
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
            } else if  (temp.getType().equals("Admin")) {
                System.out.println("done");
                Iterable<WaitingStores> getStores = waitingStoresReopsitory.findAll();
                List<WaitingStores> StoresList = new ArrayList<WaitingStores>();
                for(WaitingStores stores :getStores )
                {
                    StoresList.add(stores);
                }
                model.addAttribute("Stores",StoresList);
                return "ShowStores" ;
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
    @RequestMapping(value = "/AcceptWaitingStores",method = RequestMethod.GET)
    public String AcceptWaitingStoresGet(/*Model model  ,*/  HttpServletRequest request, @RequestParam("id") String id)
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
            } else if  (temp.getType().equals("Admin")) {
                System.out.println("done");
                int Id = Integer.parseInt(id);
                Optional<WaitingStores> getStores = waitingStoresReopsitory.findById(Id);
                if (getStores.get().isAcceptable()==0)
                {
                    getStores.get().setAcceptable(1);
                    waitingStoresReopsitory.save(getStores.get());
                    StoreEntity storeEntity = new StoreEntity();
                    storeEntity.setName(getStores.get().getName());
                    storeEntity.setOwner(getStores.get().getOwner());
                    storeReopsitory.save(storeEntity);
                }
                //model.addAttribute("waitingStores",new WaitingStores());
                return "redirect:/ShowStores";
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
    @GetMapping("/AddWaitingStore")
    public String AddWaitingStoreGet(Model model)
    {
        model.addAttribute("waitingStores",new WaitingStores());
        return "AddWaitingStore";
    }
    @PostMapping("/AddWaitingStore")
    public String AddWaitingStorePost(Model model , @ModelAttribute WaitingStores waitingStores,  HttpServletRequest request)
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
                waitingStoresReopsitory.save(waitingStores);
                model.addAttribute("waitingStores",new WaitingStores());
                return "AddWaitingStore";
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
    @RequestMapping(value = "/getSpceficStore",method = RequestMethod.GET)
    public String getSpceficStore(Model model,  HttpServletRequest request , @RequestParam("id") String id  )
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
                Iterable<StoreEntity> storeEntities = storeReopsitory.findAll();
                List<StoreEntity> AllStore = new ArrayList<StoreEntity>();
                List<StoreEntity> SpecificStore = new ArrayList<StoreEntity>();
                for(StoreEntity store :storeEntities )
                {
                    AllStore.add(store);
                }
                for(int i=0;i<AllStore.size();i++)
                {
                    if(AllStore.get(i).getOwner().equals(temp.getEmail()))
                    {
                        SpecificStore.add(AllStore.get(i));
                    }
                }
                model.addAttribute("Store",SpecificStore);
                model.addAttribute("ID",id);
                return "getSpceficStore" ;
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
}
