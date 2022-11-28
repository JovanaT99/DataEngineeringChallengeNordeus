package com.nordeus.challenge.model;


import jdk.jfr.Enabled;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private long event_id;
    private long event_timestamp;
    private String event_type;
    private EventData event_data;


}
