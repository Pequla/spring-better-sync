package com.pequla.sync.controller;

import com.pequla.sync.model.MemberModel;
import com.pequla.sync.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public List<MemberModel> getMembers() {
        return memberService.getMappedMembers();
    }

}
