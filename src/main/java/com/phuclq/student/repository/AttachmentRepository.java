package com.phuclq.student.repository;

import com.phuclq.student.domain.Attachment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

  List<Attachment> findAllByRequestId(Integer requestId);

  List<Attachment> findAllByRequestIdInAndFileTypeIn(List<Integer> requestId,List<String> typeFile);
  List<Attachment> findAllByRequestIdAndFileTypeIn(Integer requestId,List<String> typeFile);

  List<Attachment> findAllByCodeFileNotNull();

  List<Attachment> findAllByRequestIdAndFileType(Integer requestId,String fileType);
  Optional<Attachment> findAllByIdAndFileType(Long id,String fileType);

}
