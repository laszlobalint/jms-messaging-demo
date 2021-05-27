package hu.balint.laszlo.jmsmessagingdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntroMessage implements Serializable {

    private static final long serialVersionUID = 4571950173538281824L;

    private UUID id;

    private String message;
}
