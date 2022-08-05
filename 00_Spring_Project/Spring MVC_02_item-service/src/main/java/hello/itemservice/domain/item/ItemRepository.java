package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 안에 @Component가 있어 자동으로 컴포넌트 스캔 대상이 된다.
@Repository
public class ItemRepository {

    // 쓰레드를 여러 개 사용할 때에는 ConcurrentHashMap을 사용해야 한다.
    // long의 경우에도 atomic 등을 사용해냐 한다.
    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L; // static

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){
        // update를 할 때에는 Update용 ItemParamDto와 같이 클래스가 따로 있는 것이 좋다.
        // 중복 vs 명확성 -> 명확성을 따르는 것이 좋다
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}
