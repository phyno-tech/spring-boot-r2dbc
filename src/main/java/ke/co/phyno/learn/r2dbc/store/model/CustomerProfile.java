package ke.co.phyno.learn.r2dbc.store.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@ToString
@EqualsAndHashCode
@Data
@Table(value = "CUSTOMER_PROFILE")
public class CustomerProfile implements Serializable {
    @JsonIgnore
    @Id
    @Column(value = "ID")
    private Long id;

    @JsonProperty(value = "id", index = 0, access = JsonProperty.Access.READ_ONLY)
    @Column(value = "LOG_ID")
    private UUID logId;

    @Column(value = "FIRST_NAME")
    private String firstName;

    @Column(value = "LAST_NAME")
    private String lastName;

    @JsonFormat(timezone = "Africa/Nairobi", pattern = "YYYY-MM-dd HH:mm")
    @CreatedDate
    @Column(value = "CREATION_DATE")
    private Timestamp creationDate;
}
