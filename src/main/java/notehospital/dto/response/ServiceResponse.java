package notehospital.dto.response;

import lombok.*;
import notehospital.entity.Account;
import notehospital.entity.Facility;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceResponse {
    long id;
    String image;
    String name;
    Double price;
    String description;
    private Facility facility;

    public ServiceResponse(long id, String image, String name, Double price, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
