package ke.co.phyno.learn.r2dbc.data.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterRequest implements Serializable {
    @ApiModelProperty(example = "Phelix")
    @JsonProperty("firstName")
    private String firstName;

    @ApiModelProperty(example = "Ochibooh")
    @JsonProperty("lastName")
    private String lastName;
}
