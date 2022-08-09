package hello.prac.Controller;

import hello.prac.domain.Item;
import hello.prac.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class ItemBasicController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String showItems(Model model){
        // 상품 목록 보여주기
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String detailItem(@PathVariable Long itemId, Model model){
        // 상품 상세 화면 보여주기
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "/basic/item";
    }

    @GetMapping("/add")
    public String add(){
        // 상품 등록 화면 보여주기
        return "/basic/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item){
        // 전달 받은 상품 저장하기
        // html form 전송 방식으로 받았기 때문에 @ModelAttribute 사용
        itemRepository.save(item);
        return "/basic/item";
    }


    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Model model){
        // 해당 상품에 대한 정보를 받아서 editForm 창을 보여준다.
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/editForm";
    }

    @PostMapping("{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @ModelAttribute Item item){
        // 전달받은 정보를 적용하고 전달한다.
        itemRepository.updateItem(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
