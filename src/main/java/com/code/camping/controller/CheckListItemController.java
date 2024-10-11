package com.code.camping.controller;

import com.code.camping.entity.CheckListItem;
import com.code.camping.service.impl.CheckListItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/checklist/{checklistId}/item")
public class CheckListItemController {
    @Autowired
    private CheckListItemService checklistItemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<CheckListItem> getItemById(@PathVariable Long checklistId, @PathVariable Long itemId) {
        CheckListItem item = checklistItemService.getItemById(checklistId, itemId);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<CheckListItem> createItem(@PathVariable Long checklistId, @RequestBody CheckListItem item) {
        CheckListItem createdItem = checklistItemService.createItem(checklistId, item);
        return ResponseEntity.status(201).body(createdItem);
    }

    @PutMapping("/{itemId}/rename")
    public ResponseEntity<CheckListItem> renameItem(@PathVariable Long checklistId, @PathVariable Long itemId,
                                                    @RequestBody String newName) {
        CheckListItem renamedItem = checklistItemService.renameItem(checklistId, itemId, newName);
        return ResponseEntity.ok(renamedItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long checklistId, @PathVariable Long itemId) {
        checklistItemService.deleteItem(checklistId, itemId);
        return ResponseEntity.noContent().build();
    }
}
