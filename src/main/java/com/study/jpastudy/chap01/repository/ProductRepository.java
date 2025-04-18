package com.study.jpastudy.chap01.repository;

import com.study.jpastudy.chap01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

// JpaRepository를 상속한 후 첫번쨰 제네릭에 엔터티클래스 타입,
// 두번쨰에 PK의 타입을 작성합니다.
public interface ProductRepository extends JpaRepository<Product, Long> {

}
