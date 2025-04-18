package com.study.jpastudy.chap01.repository;

import com.study.jpastudy.chap01.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 테스트 이후 rollback이 진행되어서 테스트 이전의 DB 환경으로 돌아갑니다.
@Rollback(false) // 롤백 방지 -> 테스트 결과가 DB에 그대로 반영.
class ProductRepositoryTest {

    @Autowired
    EntityManager em;  // JPA에서 엔터티를 다룰 수 있게 해주는 객체.
    
    @Test
    @DisplayName("저장된 상품을 조회한다")
    void findByIdTest() {
        //given
        Long id = 1L;
        //when
        Product product = em.find(Product.class, id);
        //then
        assertEquals(product.getName(), "신발");
    }
    

    @Test
    @DisplayName("상품을 데이터베이스에 저장한다.")
    void insertTest() {
        // given
        Product product = new Product();
        product.setName("신발");
        product.setPrice(90000);
        product.setCategory(Product.Category.FASHION);

        // when
        em.persist(product);

        // then
    }
    
    @Test
    @DisplayName("영속성 컨텍스트의 1차 캐시 사용")
    void persistTest() {
        // given
        Product product = new Product();
        product.setName("짜장면");
        product.setPrice(8000);
        product.setCategory(Product.Category.FOOD);
        
        // when
        em.persist(product); // Insert

        Product foundProd = em.find(Product.class, 2L); // Insert한 정보 바로 찾아내겠다

        // then
        assertEquals(8000, foundProd.getPrice()); // 찾아낸 값 맞는지 확인
    }

    @Test
    @DisplayName("특정 상품의 가격을 수정한다.")
    void updateTest() {
        // given
        Product noodle = em.find(Product.class, 2L);

        // when
        noodle.setPrice(5000);
        noodle.setName("미니짜장면");

        // 엔터티를 변경 한 후에 영속성 컨텍스트에 넣어놓으면
        // 변경 사항을 감지하여 update를 반영합니다.
        em.persist(noodle);

        em.flush(); // 지금까지 반영된 영속성 컨텍스트를 DB에 바로 적용.
        em.clear(); // 영속성 컨텍스트 비우기

        // 영속성 컨텍스트가 비었기 때문에 다시 SELECT가 진행됨
        Product foundProd = em.find(Product.class, 2L);

        // then

    }

}