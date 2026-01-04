package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.TeamMember;
import com.example.E_Turnuva.repository.TeamMemberRepository;
import com.example.E_Turnuva.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamMemberService {
    @Autowired private TeamMemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;

    public void addMemberToTeam(Long teamId, String playerName, String role) {
        Team team = teamRepository.findById(teamId).orElseThrow();

        TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setPlayerName(playerName);
        member.setRole(role);

        memberRepository.save(member);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}