package com.example.paintapi.Controller;

import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.paintapi.dto.PaintDto;
import com.example.paintapi.paint.Paint;
import com.example.paintapi.service.PaintService;

@RestController
@RequestMapping("/api/paint")
public class PaintController {
    private final PaintService paintService;

    public PaintController(PaintService paintService) {
        this.paintService = paintService;
    }

    @PostMapping("/add")
    //curl -X POST http://localhost:8080/api/paint/add -H "Content-Type: application/json" -d "{\"name\":\"赤1号\",\"type\":\"アクリル\",\"category\":\"暖色\",\"red\":255,\"green\":0,\"blue\":0,\"amount\":10}"

    public Paint addPaint(@RequestBody PaintDto paintDto) {
        return paintService.add(paintDto);
    }

    @PutMapping("/decrease/name")
    //curl -X PUT --get --data-urlencode "name=赤1号" --data-urlencode "amount=2" http://localhost:8080/api/paint/decrease/name
    public void decreaseByName(@RequestParam String name, @RequestParam int amount) {
        paintService.decreaseByName(name, amount);
    }

    @PutMapping("/decrease/id")
    //curl -X PUT --get --data-urlencode "id=7" --data-urlencode "amount=3" http://localhost:8080/api/paint/decrease/id
    public void decreaseById(@RequestParam Long id, @RequestParam int amount) {
        paintService.rebuildWithAdjustedAmount(id, amount);
    }

    //void decreaseById(Long id,int Amount);
    //void decreaseByName(String name,int Amount);

    @GetMapping("/similar")
    //curl --get --data-urlencode "r=255" --data-urlencode "g=0" --data-urlencode "b=0" --data-urlencode "margin=30" http://localhost:8080/api/paint/similar
    public List<Paint> searchSimilarColor(
            @RequestParam int r,
            @RequestParam int g,
            @RequestParam int b,
            @RequestParam(defaultValue = "30") int margin) {
        return paintService.searchSimilarColor(r, g, b, margin);
    }

    @GetMapping("/all")
    //C:\Users\yamay\Downloads\svgtopng>curl http://localhost:8080/api/paint/all
    public List<Paint> GetAllPaints() {
        return paintService.getAllPaints();
    }

    @PostMapping("/merge")
    public ResponseEntity<String> normalizePaints() {
        paintService.mergeDuplicates();
        return ResponseEntity.ok("塗料データの統合が完了しました");
    }

    @GetMapping("/search/name")
    //curl --get --data-urlencode "name=赤1号" http://localhost:8080/api/paint/search/name
    public List<Paint> searchByName(@RequestParam("name") String name) {
        return paintService.searchByName(name);
    }

    @GetMapping("/search/name_c")
    //curl --get --data-urlencode "name=赤" http://localhost:8080/api/paint/search/name_c
    public List<Paint> searchByNameC(@RequestParam("name") String name) {
        return paintService.searchByNameContaining(name);
    }

    @GetMapping("/search/category")
    //curl --get --data-urlencode "category=暖色" http://localhost:8080/api/paint/search/category
    public List<Paint> searchByCategory(@RequestParam("category") String category) {
        return paintService.searchByCategory(category);
    }

    @GetMapping("/search/category_c")
    //curl --get --data-urlencode "category=色" http://localhost:8080/api/paint/search/category_c
    public List<Paint> searchByCategoryC(@RequestParam("category") String category) {
        return paintService.searchByCategoryContaining(category);
    }

    //ID指定で削除
    //curl -X DELETE http://localhost:8080/api/paint/delete/id/1
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        paintService.deleteById(id);
        return ResponseEntity.ok("ID:" + id + "の削除が完了しました");
    }
    //名前指定で削除
    //curl -X DELETE --get --data-urlencode "name=赤1号" http://localhost:8080/api/paint/delete/name

    @DeleteMapping("/delete/name")
    public ResponseEntity<String> deleteByName(@RequestParam String name) {
        paintService.deleteByName(name);
        return ResponseEntity.ok("名前:" + name + "の削除が完了しました");
    }

    //TYPE指定で削除
    @DeleteMapping("/delete/type")
    //curl -X DELETE --get --data-urlencode "type=アクリル" http://localhost:8080/api/paint/delete/type
    public ResponseEntity<String> deleteByType(@RequestParam String type) {
        paintService.deleteByType(type);
        return ResponseEntity.ok("タイプ:" + type + "の削除が完了しました");
    }

    @DeleteMapping("/delete/category")
    //curl -X DELETE --get --data-urlencode "category=青系" http://localhost:8080/api/paint/delete/category
    public ResponseEntity<String> deleteByCategory(@RequestParam String category) {
        paintService.deleteByCategory(category);
        return ResponseEntity.ok("カテゴリー:" + category + "の削除が完了しました");
    }

    //全件削除
    //curl -X DELETE http://localhost:8080/api/paint/delete/all
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteByAll() {
        paintService.deleteAll();
        return ResponseEntity.ok("全件削除が完了しました");
    }

    @PostMapping("/import")
    public void importPaints(@RequestParam("file") MultipartFile file) {
        paintService.importPaints(file);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportPaints() {
        byte[] data = paintService.exportPaintsAsJson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("paints_export.json").build());

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
