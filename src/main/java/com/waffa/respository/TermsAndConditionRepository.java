package com.waffa.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waffa.entity.TermsAndCondition;

@Repository("termsAndConditionRepository")
public interface TermsAndConditionRepository extends JpaRepository<TermsAndCondition,Integer> {
 public TermsAndCondition findOneById(int Id); 
}
