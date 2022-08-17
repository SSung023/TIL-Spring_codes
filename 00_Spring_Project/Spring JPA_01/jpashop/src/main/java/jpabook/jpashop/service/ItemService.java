package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }


    @Transactional // 변경감지기능 사용
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        // findItem은 영속 상태이다
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    @Transactional // 변경감지기능 사용
    public void updateItemDto(Long itemId, UpdateItemDTO updateItemDTO){
        // findItem은 영속 상태이다
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(updateItemDTO.getName());
        findItem.setPrice(updateItemDTO.getPrice());
        findItem.setStockQuantity(updateItemDTO.getStockQuantity());
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
