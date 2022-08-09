package hello.prac.repository;

import hello.prac.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static Long sequence = 0L;


    public Item save(Item item){
        // sequence를 1 증가시켜서 item에 적용하고, store에 저장
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long itemId){
        Item findItem = store.get(itemId);
        return findItem;
    }

    public List<Item> findAll(){
        ArrayList<Item> items = new ArrayList<>(store.values());
        return items;
    }

    public Item updateItem(Long itemId, Item updateParam){
        Item targetItem = store.get(itemId);

        targetItem.setId(updateParam.getId());
        targetItem.setItemName(updateParam.getItemName());
        targetItem.setPrice(updateParam.getPrice());

        return targetItem;
    }

    /**
     * test 용도
     */
    public void clearAll(){
        store.clear();
    }
}
