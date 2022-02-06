package io.kuoche.post.text.periphery.data;

import io.kuoche.post.text.core.boundary.exit.LikeRepository;
import io.kuoche.post.text.core.domain.Like;
import io.kuoche.post.text.periphery.data.mapper.LikeMapper;
import io.kuoche.post.text.periphery.data.model.LikeDo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {
    private final LikeMapper likeMapper;

    @Override
    public boolean save(Like like) {
        boolean success = false;
        try{
            likeMapper.save(toDo(like));
            success = true;
        }catch (DuplicateKeyException e){
            success = true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return success;
    }

    @Override
    public List<Like> findAllByTextId(Long textId) {
        List<LikeDo> likes = new ArrayList<>();
        try{
            likes = likeMapper.findAllByTextId(textId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return likes.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Like like) {
        LikeDo likeDo = toDo(like);
        boolean success = false;
        try{
            likeMapper.delete(likeDo);
            success = true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return success;
    }

    private Like toEntity(LikeDo likeDo){
        return new Like(
                likeDo.getUserId(),
                likeDo.getTextId()
        );
    }

    private LikeDo toDo(Like like){
        LikeDo likeDo = new LikeDo();
        likeDo.setUserId(like.getUserId());
        likeDo.setTextId(like.getTextId());
        return likeDo;
    }
}
