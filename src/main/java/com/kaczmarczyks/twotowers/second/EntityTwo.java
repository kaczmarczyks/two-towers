package com.kaczmarczyks.twotowers.second;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
public class EntityTwo {

    @Id
    @GeneratedValue(generator = "entity_two_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "entity_two_seq_gen", sequenceName = "entity_two_sequence")
    Long id;

    String propertyTwo;
}
