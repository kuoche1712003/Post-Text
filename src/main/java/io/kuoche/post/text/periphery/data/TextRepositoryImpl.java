package io.kuoche.post.text.periphery.data;

import io.kuoche.post.text.core.boundary.exit.TextRepository;
import io.kuoche.post.text.core.domain.Comment;
import io.kuoche.post.text.core.domain.Text;
import io.kuoche.post.text.periphery.data.mapper.CommentMapper;
import io.kuoche.post.text.periphery.data.mapper.TextMapper;
import io.kuoche.post.text.periphery.data.model.CommentDo;
import io.kuoche.post.text.periphery.data.model.TextDo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class TextRepositoryImpl implements TextRepository {
    private final TextMapper textMapper;
    private final CommentMapper commentMapper;
    private final DataSourceTransactionManager transactionManager;

    @Override
    public Long save(Text text){
        TextDo textDo = toDo(text);
        try{
            textMapper.save(textDo);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return textDo.getTextId();
    }

    @Override
    public Optional<Text> findByTextId(Long textId) {
        Optional<TextDo> textDoOptional = Optional.empty();
        try{
            textDoOptional = textMapper.findByTextId(textId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(!textDoOptional.isPresent())
            return Optional.empty();
        Text text = toEntity(textDoOptional.get());
        return Optional.of(text);
    }

    @Override
    public List<Text> findAllByUserId(String userId) {
        List<TextDo> texts = new ArrayList<>();
        try{
            texts = textMapper.findAllByUserId(userId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return texts.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long textId) {
        boolean success = false;
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionAttribute());
        try{
            textMapper.delete(textId);
            commentMapper.deleteByTextId(textId);
            transactionManager.commit(status);
            success = true;
        }catch (Exception e){
            log.error(e.getMessage());
            transactionManager.rollback(status);
        }
        return success;
    }

    private TextDo toDo(Text text){
        TextDo textDo = new TextDo();
        textDo.setTextId(text.getTextId());
        textDo.setUserId(text.getUserId());
        textDo.setContent(text.getContent());
        textDo.setPostTime(text.getPostTime());
        return textDo;
    }
    private Text toEntity(TextDo textDo){
        return new Text(
                textDo.getTextId(),
                textDo.getUserId(),
                textDo.getContent(),
                textDo.getPostTime()
        );
    }
}
