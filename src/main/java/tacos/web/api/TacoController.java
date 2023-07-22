package tacos.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos",
        produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {
    private final TacoRepository tacoRepo;
    private final OrderRepository orderRepo;


    public TacoController(TacoRepository tacoRepo
            , OrderRepository orderRepo) {
        this.tacoRepo = tacoRepo;
        this.orderRepo = orderRepo;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        return tacoRepo.findById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder putOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody TacoOrder order) {
        order.setId(orderId);
        return orderRepo.save(order);
    }

    @PatchMapping(path="/{orderId}", consumes = "application/json")
    public TacoOrder pathOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody TacoOrder patch) {
        Optional<TacoOrder> optTacoOrder = orderRepo.findById(orderId);

        if (optTacoOrder.isPresent()) {
            TacoOrder order = optTacoOrder.get();
            if (patch.getDeliveryName() != null) {
                order.setDeliveryStreet(patch.getDeliveryStreet());
            }
            if (patch.getDeliveryStreet() != null) {
                order.setDeliveryStreet(patch.getDeliveryStreet());
            }
            if (patch.getDeliveryState() != null) {
                order.setDeliveryState(patch.getDeliveryState());
            }
            if (patch.getDeliveryZip() != null) {
                order.setDeliveryZip(patch.getDeliveryZip());
            }
            if(patch.getCcNumber()!=null){
                order.setCcNumber(patch.getCcNumber());
            }
            if(patch.getCcExpiration()!=null){
                order.setCcExpiration(patch.getCcExpiration());
            }
            if(patch.getCcCVV()!=null){
                order.setCcCVV(patch.getCcCVV());
            }
            return orderRepo.save(order);
        }
        return null;
    }
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepo.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {}
    }

}
