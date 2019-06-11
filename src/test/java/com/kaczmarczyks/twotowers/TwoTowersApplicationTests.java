package com.kaczmarczyks.twotowers;

import com.kaczmarczyks.twotowers.first.EntityOne;
import com.kaczmarczyks.twotowers.first.EntityOneRepository;
import com.kaczmarczyks.twotowers.second.EntityTwo;
import com.kaczmarczyks.twotowers.second.EntityTwoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwoTowersApplicationTests {

    @Autowired
    private EntityOneRepository entityOneRepository;

    @Autowired
    private EntityTwoRepository entityTwoRepository;

    @Test
    public void contextLoads() {
    }


    @Test
    public void entityOneIsSaved() {

        //given
        EntityOne entityOne = new EntityOne(null, "propertyOne1");

        //when
        entityOneRepository.save(entityOne);

        //then
        assertThat(entityOne.getId()).isNotNull();
    }

    @Test
    public void entityTwoIsSaved() {

        //given
        EntityTwo entityTwo = new EntityTwo(null, "propertyTwo1");

        //when
        entityTwoRepository.save(entityTwo);

        //then
        assertThat(entityTwo.getId()).isNotNull();
    }
}