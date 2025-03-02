package com.tico.pomoro_do.domain.category.repository;


import com.tico.pomoro_do.domain.category.entity.CategoryInvitation;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryInvitationRepository extends JpaRepository<CategoryInvitation, Long> {

    List<CategoryInvitation> findAllByInviteeAndStatusOrderByCreatedAtDesc(User invitee, CategoryInvitationStatus status);

}
