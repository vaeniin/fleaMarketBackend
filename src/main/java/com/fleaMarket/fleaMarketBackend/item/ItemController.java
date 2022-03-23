package com.fleaMarket.fleaMarketBackend.item;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fleaMarket.fleaMarketBackend.utils.FileUpload;
import com.fleaMarket.fleaMarketBackend.utils.Image;
import com.fleaMarket.fleaMarketBackend.utils.Location;
import com.fleaMarket.fleaMarketBackend.utils.SetImagesLength;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemRepository repository;
	
	private final int pageSize = 10;
	
	@PostMapping("/add")
	public ResponseEntity<String> add(
			@RequestParam(name="files", required=false) MultipartFile[] files,
			@RequestParam(name="images", required=false) String[] images,
			Location location,
			Item item
		) throws IOException {
		String itemId = item.getId();
		if (itemId == null) itemId = "1";
		Optional<Item> checkItem = repository.findById(itemId);
		
		int imagesLength = SetImagesLength.setLength(images, files);
		if (imagesLength > 0) {
			Image[] newImages = new Image[imagesLength];
			
			if (images != null) {
				for (int i = 0; i < images.length; i++) {
					Image image = new Image();
					String[] data = images[i].split("/");
					image.setName(data[0]);
					image.setHeight(data[1]);
					image.setWidth(data[2]);
					newImages[i] = image;
				}
			}
			
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					String[] file = StringUtils.cleanPath(files[i].getOriginalFilename()).split("/");
					String fileName = file[0];
					String height = file[1];
					String width = file[2];

					Image image = new Image();
					image.setName(fileName);
					image.setHeight(height);
					image.setWidth(width);
					if (images != null) newImages[images.length + i] = image;
					else newImages[i] = image;
					
					FileUpload.saveFile(item.getUserId(), fileName, files[i]);
				}
			}
			
			item.setImages(newImages);
		}
		
		if (checkItem.isEmpty()) item.setDate(new Date());
		item.setLocation(location);
		
		repository.save(item);

		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/getAll")
	public ResponseEntity<List<ItemShort>> getAll(@RequestBody SearchParams params) {
		Pageable pageable = PageRequest.of(params.getPage(), pageSize, Sort.by(Sort.Direction.DESC, "date"));
		return ResponseEntity.ok().body(repository.findItems(params, pageable));
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Optional<ItemResult>> get(@PathVariable String id) {
		return ResponseEntity.ok().body(repository.findItemById(id));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		Optional<Item> item = repository.findById(id);
		
		if (item.isPresent()) {
			String userId = item.get().getUserId();
			Image[] imgs = item.get().getImages();
			if (imgs != null) {
				for (Image img : imgs) {
					FileUpload.deleteFile(userId, img.getName());
				}
			}
		}
		
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@Getter
	@Setter
	private class ImageReq {
		private String name;
	}
}
