package ke.co.phyno.learn.r2dbc.utils;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class SharedUtils {
    public boolean isNullOrEmptyOrBlank(String s) {
        return s == null || s.isEmpty() || s.isBlank();
    }
}
