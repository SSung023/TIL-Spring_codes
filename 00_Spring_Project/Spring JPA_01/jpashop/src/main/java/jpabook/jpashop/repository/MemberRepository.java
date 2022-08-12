package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext // spring이 EntityManager를 생성 후 주입해줌
    private final EntityManager em;


    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        // JPA가 지원하는 기능 - Class와 PK를 넘기면 찾아준다.
        // 단건 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        // JPQL 작성 - table이 아닌 entity를 기준으로 쿼리를 전달한다
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
