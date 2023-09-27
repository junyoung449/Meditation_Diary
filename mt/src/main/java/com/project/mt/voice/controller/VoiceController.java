package com.project.mt.voice.controller;

import com.project.mt.fileupload.config.AwsS3Uploader;
import com.project.mt.voice.dto.request.VoiceRequest;
import com.project.mt.voice.repository.VoiceRepository;
import com.project.mt.voice.service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/voice")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VoiceController {

    private final AwsS3Uploader awsS3Uploader;
    private final VoiceService voiceService;

    @PostMapping
    public ResponseEntity<?> saveVoice(@RequestParam MultipartFile voice, @RequestParam VoiceRequest voiceRequest)
            throws IOException {
        Map<String, Object> response = new HashMap<>();

        List<MultipartFile> list = new ArrayList<>();

        list.add(voice);

        String[] voiceUrl = awsS3Uploader.upload(list, "voice");

        response.put("voiceIdx", voiceService.saveVoice(voiceRequest, voiceUrl[0]));

        return ResponseEntity.ok(response);
    }
}
