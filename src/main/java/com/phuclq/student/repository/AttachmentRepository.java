package com.phuclq.student.repository;

import com.phuclq.student.domain.Attachment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

  List<Attachment> findAllByRequestId(Integer requestId);

  List<Attachment> findAllByRequestIdAndFileType(Integer requestId,String fileType);

}
