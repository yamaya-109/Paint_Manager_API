package com.example.paintapi.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.paintapi.dto.PaintDto;
import com.example.paintapi.paint.Paint;
import com.example.paintapi.repository.PaintRepository;
import com.example.paintapi.repository.UserRepository;
import com.example.paintapi.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaintServiceImpl implements PaintService {

    private final PaintRepository paintRepository;
    private final UserRepository userRepository;

    public PaintServiceImpl(PaintRepository paintRepository, UserRepository userRepository) {
        this.paintRepository = paintRepository;
        this.userRepository = userRepository;
        // TODO 自動生成されたコンストラクター・スタブ
    }

    @Override
    public Paint rebuildWithAdjustedAmount(Long id, int amount)//IDを元に追加
    {
        Paint paint = paintRepository.findById(id).orElseThrow(() -> new RuntimeException("Paint not found"));
        if (paint.getAmount() + amount <= 0) {
            deleteById(id);
            return null;
        }
        Paint nPaint = withAmount(paint.getName(), paint.getType(), paint.getCategory(), paint.getRed(),
                paint.getGreen(), paint.getBlue(), paint.getAmount() + amount, paint.getUser());

        deleteById(id);
        return paintRepository.save(nPaint);

    }

    //追加
    @Override
    public Paint add(PaintDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        Optional<Paint> existing = paintRepository.findByNameAndTypeAndRedAndGreenAndBlueAndUser(
                dto.getName(), dto.getType(), dto.getRed(), dto.getGreen(), dto.getBlue(), user);

        if (existing.isPresent()) {

            Paint paint = existing.get();

            return rebuildWithAdjustedAmount(paint.getId(), dto.getAmount());

        }

        Paint paint = new Paint(dto.getName(), dto.getType(), dto.getCategory(), dto.getRed(), dto.getGreen(),
                dto.getBlue(), dto.getAmount(), user);

        return paintRepository.save(paint);
    }

    @Override
    public void decreaseByName(String name, int amount) {
        //List<Paint> paints = paintRepository.findByName(name);

        //for (Paint paint : paints) {
        //Paint wsPaint= rebuildWithAdjustedAmount(paint.getId(), amount);  // こちらは Repository の update クエリでOK
        //if(wsPaint !=null)
        //{
        //paintRepository.save(wsPaint);
        //}
        //}

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        List<Paint> paints = paintRepository.findByNameAndUser(name, user);

        for (Paint paint : paints) {
            Paint updatedPaint = rebuildWithAdjustedAmount(paint.getId(), amount);
            if (updatedPaint != null) {
                paintRepository.save(updatedPaint);
            }
        }
    }

    private Paint withAmount(String name, String type, String category, int red, int green, int blue, int amount,
            User user) {
        Paint paint = new Paint(name, type, category, red, green, blue, amount, user);
        return paint;
    }

    @Transactional
    public void mergeDuplicates() {
        List<Paint> allPaints = paintRepository.findAll();

        Map<String, Paint> merged = new HashMap<>();

        for (Paint paint : allPaints) {
            String key = paint.getUser().getuserName() + "|" + paint.getName() + "|" + paint.getType() + "|"
                    + paint.getRed() + "|" + paint.getGreen() + "|" + paint.getBlue();
            if (merged.containsKey(key)) {
                Paint existing = merged.get(key);
                Paint mergedPaint = rebuildWithAdjustedAmount(existing.getId(), paint.getAmount());
                if (mergedPaint != null) {
                    merged.put(key, mergedPaint);
                }
                paintRepository.delete(paint);
            } else {
                merged.put(key, paint);
            }
        }
        for (Paint paint : merged.values()) {
            paintRepository.save(paint);
        }
    }

    @Override
    //curl "http://localhost:8080/api/paint/search" --get --data-urlencode "name=検索ワード"
    public List<Paint> searchByName(String name) {
        return paintRepository.findByName(name);
    }

    @Override
    //curl "http://localhost:8080/api/paint/search/name_c" --get --data-urlencode "name=検索ワード"
    public List<Paint> searchByNameContaining(String name) {
        return paintRepository.findByNameContaining(name);
    }

    @Override
    //curl "http://localhost:8080/api/paint/search/category" --get --data-urlencode "category=色系"
    public List<Paint> searchByCategory(String category) {
        return paintRepository.findByCategory(category);
    }

    @Override
    //curl "http://localhost:8080/api/paint/search/category_c" --get --data-urlencode "category=色系"
    public List<Paint> searchByCategoryContaining(String category) {
        return paintRepository.findByCategoryContaining(category);
    }

    @Override
    public List<Paint> searchSimilarColor(int r, int g, int b, int margin) {
        return paintRepository.findByRedBetweenAndGreenBetweenAndBlueBetween(
                Math.max(0, r - margin), Math.min(255, r + margin),
                Math.max(0, g - margin), Math.min(255, g + margin),
                Math.max(0, b - margin), Math.min(255, b + margin));
    }

    @Override
    //curl "http://localhost:8080/api/paint/search" --get --data-urlencode "name=検索ワード"
    public List<Paint> searchByType(String Type) {
        return paintRepository.findByType(Type);
    }

    @Override
    //curl "http://localhost:8080/api/paint/search/name_c" --get --data-urlencode "name=検索ワード"
    public List<Paint> searchByTypeContaining(String Type) {
        return paintRepository.findByTypeContaining(Type);
    }

    @Override
    public List<Paint> getAllPaints() {
        return paintRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        paintRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        List<Paint> paints = paintRepository.findByName(name);
        paintRepository.deleteAll(paints);
    }

    @Override
    public void deleteByType(String type) {
        List<Paint> paints = paintRepository.findByType(type);
        paintRepository.deleteAll(paints);
    }

    @Override
    public void deleteByCategory(String category) {
        List<Paint> paints = paintRepository.findByCategory(category);
        paintRepository.deleteAll(paints);

    }

    @Override
    public void deleteAll() {
        paintRepository.deleteAll();
    }

    @Override
    public ResponseEntity<String> importPaints(MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<PaintDto> paintDtos = Arrays.asList(mapper.readValue(file.getInputStream(), PaintDto[].class));
            for (PaintDto dto : paintDtos) {
                this.add(dto); // paintService ではなく this でOK
            }
            return ResponseEntity.ok("インポートが完了しました");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("ファイルの読み取りに失敗しました: " + e.getMessage());
        }
    }

    @Override
    public byte[] exportPaintsAsJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Paint> paints = paintRepository.findAll();
            return mapper.writeValueAsBytes(paints);
        } catch (Exception e) {
            throw new RuntimeException("JSON変換に失敗しました。");
        }
    }

    @Override
    public List<Paint> findByNameAndOownUser(String name, User user) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

}
