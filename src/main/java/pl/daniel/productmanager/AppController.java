package pl.daniel.productmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {
    private ProductService productService;
    @Autowired
    public AppController(ProductService productService) {
        this.productService = productService;
    }
    @RequestMapping("/")
    public String viewHomePage(Model model){
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts",listProducts);
        return "index";
    }
    @RequestMapping("/new")
    public String showNewProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "new_product";
    }
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/";
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductForm(@PathVariable(name="id") Long id){
        ModelAndView modelAndView = new ModelAndView("edit_product");
        Product product = productService.get(id);
        modelAndView.addObject("product",product);
        return modelAndView;

    }
    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id){
        productService.delete(id);
        return "redirect:/";
    }
}
