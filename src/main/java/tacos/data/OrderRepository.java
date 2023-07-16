package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.TacoOrder;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByDeliveryStateAndDeliveryCity(
            String DeliveryState, String deliveryCity);
//    TacoOrder save(TacoOrder order);
}
