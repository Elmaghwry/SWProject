package com.sweproject.swproject.controllar;

import com.sweproject.swproject.Entities.*;
import com.sweproject.swproject.Repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Ids;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Controller
public class ProductControllar {

    @Autowired
    private ProductRepository productRepository ;
    @Autowired
    private ProductInStoreRepository productInStoreRepository;
    @Autowired
    private HistoricalReopsitory hestoricalReopsitory;
    @Autowired
    private CartReopsitory cartReopsitory;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/AddProducts")
    public String ADDProductGet(Model model)
    {
        model.addAttribute("productEntity",new ProductEntity());
        return "AddProduct";
    }
    @PostMapping("/AddProducts")
    public String ADDProductPost(Model model ,  HttpServletRequest request, @ModelAttribute ProductEntity productEntity)
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
                productEntity.setQuantity(10);
                Iterable<ProductEntity> productEntities =  productRepository.findAll();
                for(ProductEntity productEntity1 :productEntities)
                {
                    if(productEntity1.getName().equals(productEntity.getName()))
                    {
                        int Qun = productEntity.getQuantity()+1;
                        productEntity.setQuantity(Qun);
                        break;
                    }
                }
                productRepository.save(productEntity);
                model.addAttribute("productEntity",new ProductEntity());
                return "AddProduct";
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

    @GetMapping("/ShowProducts")
    public String ShowProducts(Model model ,  HttpServletRequest request)
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
            } else {
                System.out.println("done");
                Iterable<ProductEntity> getProducts = productRepository.findAll();
                List<ProductEntity> ProductList = new ArrayList<ProductEntity>();
                for(ProductEntity product :getProducts )
                {
                    ProductList.add(product);
                }
                model.addAttribute("Product",ProductList);
                return "ShowProducts" ;
            }
        }
    }
    @RequestMapping(value = "/SpecificProduct",method = RequestMethod.GET)
    public String SpecificProduct(Model model ,  HttpServletRequest request , @RequestParam("id") String id )
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
            } else {
                System.out.println("done");
                int Id = Integer.parseInt(id);
                //System.out.println("num :ffffdddddddddddddddddddddddd");
                Optional<ProductEntity> getProducts = productRepository.findById(Id);
                //System.out.println("num :hfjhjhljkkfdjgdfhgjhkgjgdh");
                int num_of_views = getProducts.get().getNumOfViews()+1;
                //System.out.println("num "+ num_of_views);
                //productRepository.updateAddress(Id,num_of_views);
                getProducts.get().setNumOfViews(num_of_views);
                productRepository.save(getProducts.get());
                //System.out.println("num bbbbbbbbbbbbbbbbbbbbb");
                double price = getProducts.get().getPrice() ;
                int NOB = temp.getNumOfBuy() ;
                String type = temp.getType();
                double present = 0;
                if(NOB==0)
                {
                    present+=0.05;
                }
                if(type.equals("StoreOwner"))
                {
                    present+=0.15;
                }
                if(NOB==2)
                {
                    present+=0.10;
                }
                getProducts.get().setPrice(price*present);
                model.addAttribute("product",getProducts.get());
                return "SpecificProduct";
            }
        }
    }
    @RequestMapping(value = "/BuyProduct",method = RequestMethod.GET)
    public String BuyProduct(Model model ,  HttpServletRequest request , @RequestParam("id") String id )
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
            } else {
                System.out.println("done");
                int Id = Integer.parseInt(id);
                //System.out.println("num :ffffdddddddddddddddddddddddd");
                Optional<ProductEntity> getProducts = productRepository.findById(Id);
                //System.out.println("num :hfjhjhljkkfdjgdfhgjhkgjgdh");
                int Qun = getProducts.get().getQuantity()-1;
                //System.out.println("num "+ num_of_views);
                //productRepository.updateAddress(Id,num_of_views);
                if(Qun>0) {
                    getProducts.get().setQuantity(Qun);
                    int numofBuy = getProducts.get().getNumOfBuy()+1;
                    getProducts.get().setNumOfBuy(numofBuy);
                    productRepository.save(getProducts.get());
                }
                //System.out.println("num bbbbbbbbbbbbbbbbbbbbb");
                model.addAttribute("product",getProducts.get());
                return "SpecificProduct";
            }
        }
    }

    @GetMapping("/MaxP")
    public String MaxProduct(Model model ,  HttpServletRequest request)
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
            } else if (temp.getType().equals("StoreOwner")||temp.getType().equals("Admin")) {
                System.out.println("done");
                Iterable<ProductEntity> getProducts = productRepository.findAll();
                List<ProductEntity> ProductList = new ArrayList<ProductEntity>();
                for(ProductEntity product :getProducts )
                {
                    ProductList.add(product);
                }
                ProductEntity product = ProductList.get(0);
                for(int i =1; i<ProductList.size();i++)
                {
                    if(ProductList.get(i).getNumOfBuy()>product.getNumOfBuy())
                    {
                        product = ProductList.get(i);
                    }
                }
                //System.out.println(product.getName() +" " +product.getNumOfViews()+" "+product.getNumOfBuy());
                model.addAttribute("product",product);
                return "StaticProduct" ;
            }
            else
            {
                return "redirect:/Home";
            }
        }
    }
    @GetMapping("/MinP")
    public String MinProduct(Model model,  HttpServletRequest request )
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
            } else if (temp.getType().equals("StoreOwner")||temp.getType().equals("Admin")) {
                System.out.println("done");
                Iterable<ProductEntity> getProducts = productRepository.findAll();
                List<ProductEntity> ProductList = new ArrayList<ProductEntity>();
                for(ProductEntity product :getProducts )
                {
                    ProductList.add(product);
                }
                ProductEntity product = ProductList.get(0);
                for(int i =1; i<ProductList.size();i++)
                {
                    if(ProductList.get(i).getNumOfBuy()<product.getNumOfBuy())
                    {
                        product = ProductList.get(i);
                    }
                }
                //System.out.println(product.getName() +" " +product.getNumOfViews()+" "+product.getNumOfBuy());
                model.addAttribute("product",product);
                return "StaticProduct" ;
            }
            else
            {
                return "redirect:/Home";
            }
        }
    }

    @RequestMapping(value = "/AddProductToStore",method = RequestMethod.GET)
    public String AddProductToStore(Model model,  HttpServletRequest request , @RequestParam("id1") String idS, @RequestParam("id2") String idP, @RequestParam("id3") String StoreName  )
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
                int IdS = Integer.parseInt(idS);
                int IdP = Integer.parseInt(idP);
                //System.out.println(IdP+"  "+IdS);
                Iterable<ProductEntity> getProducts = productRepository.findAll();
                List<ProductEntity> ProductList = new ArrayList<ProductEntity>();
                for(ProductEntity product :getProducts )
                {
                    ProductList.add(product);
                }
                ProductEntity product = new ProductEntity();
                for(int i =0; i<ProductList.size();i++)
                {
                    if(ProductList.get(i).getId()==IdP)
                    {
                        product = ProductList.get(i);
                        break;
                    }
                }
                ProductInStore productInStore = new ProductInStore();
                productInStore.setIdProduct(IdP);
                productInStore.setNameProduct(product.getName());
                productInStore.setIdStore(IdS);
                productInStore.setNameStore(StoreName);
                Historical historical = new Historical();
                historical.setIdProduct(IdP);
                historical.setBrand(product.getBrand());
                historical.setCategory(product.getCategory());
                historical.setName(product.getName());
                historical.setNum_of_views(product.getNumOfViews());
                historical.setNumOfBuy(product.getNumOfBuy());
                historical.setPrice(product.getPrice());
                historical.setQuantity(product.getQuantity());
                historical.setStoreName(StoreName);
                historical.setStoreId(IdS);
                historical.setType("ADD");
                hestoricalReopsitory.save(historical);
                productInStoreRepository.save(productInStore);
                //model.addAttribute("productInStore", new ProductInStore());
                return "HomeOwner" ;
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

    @GetMapping("/ViewH")
    public String ViewHistory(Model model ,  HttpServletRequest request )
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
                Iterable<Historical> getH = hestoricalReopsitory.findAll();
                List<Historical> HList = new ArrayList<Historical>();
                for(Historical historical :getH )
                {
                    HList.add(historical);
                }
                model.addAttribute("Historical",HList);
                return "ViewHistory" ;
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

    @RequestMapping(value = "/Undo",method = RequestMethod.GET)
    public String Undo(Model model ,  HttpServletRequest request , @RequestParam("id1") String id, @RequestParam("id2") String type )
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
                //System.out.println(id +"   "+type);
                int Id = Integer.parseInt(id);
                Iterable<Historical> getH = hestoricalReopsitory.findAll();
                List<Historical> HList = new ArrayList<Historical>();
                for(Historical historical :getH )
                {
                    HList.add(historical);
                }
                Historical historical = new Historical();
                for(int i =0; i<HList.size();i++)
                {
                    if(HList.get(i).getId()==Id)
                    {
                        historical = HList.get(i);
                        break;
                    }
                }

                if(type.equals("ADD"))
                {
                    Iterable<ProductInStore> getPS = productInStoreRepository.findAll();
                    List<ProductInStore> SList = new ArrayList<ProductInStore>();
                    for(ProductInStore productInStore :getPS )
                    {
                        SList.add(productInStore);
                    }
                    ProductInStore productInStore = new ProductInStore();
                    for(int i =0; i<SList.size();i++)
                    {
                        if(SList.get(i).getIdProduct()==historical.getIdProduct())
                        {
                            productInStore = SList.get(i);
                            break;
                        }
                    }
                    // delete record from product in store via historical product id via get all product in store
                    productInStoreRepository.delete(productInStore);
                    historical.setType("Delete");
                    hestoricalReopsitory.save(historical);
                }
                if(type.equals("Delete"))
                {
                    ProductInStore productInStore = new ProductInStore();
                    productInStore.setIdProduct(Id);
                    productInStore.setNameProduct(historical.getName());
                    productInStore.setNameStore(historical.getStoreName());
                    productInStore.setIdStore(historical.getStoreId());
                    productInStoreRepository.save(productInStore);
                    historical.setType("ADD");
                    hestoricalReopsitory.save(historical);
                }
                return "redirect:/ViewH";
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
    public int getCartID(int idP)
    {
        Iterable<Cart> carts = cartReopsitory.findAll();
        for (Cart cart : carts ) {
            if(cart.getIdProduct()==idP)
            {
                return cart.getId();
            }
        }
        return -1;
    }
    @RequestMapping(value = "/AddToCart",method = RequestMethod.GET)
    public String AddToCart(Model model ,  HttpServletRequest request , @RequestParam("id") String id )
    {
        int ID = Integer.parseInt(id);
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
            }
            else if (temp.getType().equals("Admin"))
            {
                return "redirect:/HomeAdmin";
            }
            else  {
                System.out.println("done");
                //System.out.println(id +"   "+type);
                Optional<ProductEntity> product = productRepository.findById(ID);
                int cartID = getCartID(ID);
                if (cartID==-1) {
                    Cart cart = new Cart();
                    cart.setIdProduct(product.get().getId());
                    cart.setOwner(temp.getEmail());
                    cart.setQuantity(1);
                    cart.setName(product.get().getName());
                    cartReopsitory.save(cart);
                }
                else {
                    Optional<Cart> cart = cartReopsitory.findById(cartID);
                    cart.get().setQuantity(cart.get().getQuantity()+1);
                    cartReopsitory.save(cart.get());

                }
                if (temp.getType().equals("StoreOwner")) {
                    return "redirect:/HomeOwner";
                } else {
                    return "redirect:/Home";
                }
            }
        }
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
    @GetMapping("/ConfirmBuy")
    public String ConfirmBuy(Model model ,  HttpServletRequest request )
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
            }
            else if (temp.getType().equals("Admin"))
            {
                return "redirect:/HomeAdmin";
            }
            else  {
                System.out.println("done");
                Vector<Cart> myCarts = getCartForOwner(temp.getEmail());
                int numOfBuy =0;
                for(int i =0;i<myCarts.size();i++)
                {
                    int PID = myCarts.get(i).getIdProduct();
                    Optional<ProductEntity> productEntity = productRepository.findById(PID);
                    int OQun = productEntity.get().getQuantity();
                    int Qun =  myCarts.get(i).getQuantity();
                    int NewQun =OQun-Qun;
                    if(NewQun>0)
                    {
                        int NOB = productEntity.get().getNumOfBuy();
                        int NNOB = NOB +Qun;
                        numOfBuy+=Qun;
                        productEntity.get().setNumOfBuy(NNOB);
                        productEntity.get().setQuantity(NewQun);
                        productRepository.save(productEntity.get());
                        cartReopsitory.delete(myCarts.get(i));
                    }
                    else
                    {
                        System.out.println("please remove some of items to confirm buying");
                    }

                }
                int NNOB = temp.getNumOfBuy() + numOfBuy ;
                temp.setNumOfBuy(NNOB);
                userRepository.save(temp);
            }
            if (temp.getType().equals("StoreOwner")) {
                return "redirect:/HomeOwner";
            } else {
                return "redirect:/Home";
            }

        }
    }
    @GetMapping("/MyCart")
    public String MyCart(Model model ,  HttpServletRequest request)
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
            } else {
                System.out.println("done");
                Iterable<Cart> getCarts = cartReopsitory.findAll();
                List<Cart> carts = new ArrayList<Cart>();
                for(Cart cart :getCarts )
                {
                    carts.add(cart);
                }
                model.addAttribute("Cart",carts);
                return "MyCart" ;
            }
        }
    }
    @RequestMapping(value = "/RemoveFromCart",method = RequestMethod.GET)
    public String RemoveFromCart(Model model ,  HttpServletRequest request , @RequestParam("id1") String id, @RequestParam("id2") String qun )
    {
        int ID = Integer.parseInt(id);
        int Qun = Integer.parseInt(qun);
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
            }
            else if (temp.getType().equals("Admin"))
            {
                return "redirect:/HomeAdmin";
            }
            else  {
                System.out.println("done");
                //System.out.println(id +"   "+type);
                Optional<Cart> cart = cartReopsitory.findById(ID);
                System.out.println(Qun);
                cart.get().setQuantity(cart.get().getQuantity()-Qun);
                int CartQ = cart.get().getQuantity();
                if(CartQ<=0)
                {
                    cartReopsitory.delete(cart.get());
                }
                else
                {
                    cartReopsitory.save(cart.get());
                }
                if (temp.getType().equals("StoreOwner")) {
                    return "redirect:/HomeOwner";
                } else {
                    return "redirect:/Home";
                }
            }
        }
    }

}
