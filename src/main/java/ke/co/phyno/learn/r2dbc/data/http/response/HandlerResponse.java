package ke.co.phyno.learn.r2dbc.data.http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class HandlerResponse implements Serializable {
    @Builder.Default
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer code = null;

    @Builder.Default
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String message = null;

    @Builder.Default
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Object data = null;
}
