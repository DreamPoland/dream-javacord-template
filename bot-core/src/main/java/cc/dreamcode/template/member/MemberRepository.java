package cc.dreamcode.template.member;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;
import org.javacord.api.entity.user.User;

@DocumentCollection(path = "member", keyLength = 36)
public interface MemberRepository extends DocumentRepository<String, Member> {

    default Member findOrCreate(@NonNull String idAsString, String userName) {

        Member member = this.findOrCreateByPath(idAsString);

        if (userName != null) {
            member.setName(userName);
        }

        return member;
    }

    default Member findOrCreate(@NonNull User user) {
        return this.findOrCreate(user.getIdAsString(), user.getName());
    }

}
