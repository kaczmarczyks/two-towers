package com.kaczmarczyks.twotowers.first;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
public class EntityOne {

    @Id
    @GeneratedValue(generator = "entity_one_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "entity_one_seq_gen", sequenceName = "entity_one_sequence")
    Long id;

    String propertyOne;

}
