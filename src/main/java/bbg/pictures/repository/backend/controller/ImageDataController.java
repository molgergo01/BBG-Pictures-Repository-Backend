package bbg.pictures.repository.backend.controller;

import bbg.pictures.repository.backend.model.ImageData;
import bbg.pictures.repository.backend.service.ImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/image")
public class ImageDataController {
    @Autowired
    private ImageDataService imageDataService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<ImageData> saveImage(@RequestBody final ImageData imageData) {
        imageDataService.save(imageData);

        return new ResponseEntity<>(imageData, HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Iterable<ImageData>> getImages(@RequestParam(required = false) final String uploaderName, @RequestParam(required = false) final String album) {
        final Iterable<ImageData> images = imageDataService.findAllMatchingFilters(uploaderName, album);

        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ImageData> getImage(@PathVariable final Long id) {
        final ImageData image = imageDataService.findById(id);

        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<String> updateImage(@PathVariable final Long id, @RequestBody final ImageData imageData) {
        imageDataService.update(id, imageData);

        return new ResponseEntity<>("Successfully updated image with id: '" + id + "'", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteImage(@PathVariable final Long id) {
        imageDataService.delete(id);

        //TODO Make it so an exception is thrown when the entity with the id does not exists. Currently it returns the same response as it does when it exists.
        return new ResponseEntity<>("Successfully deleted image with id: '" + id + "'", HttpStatus.OK);
    }
}