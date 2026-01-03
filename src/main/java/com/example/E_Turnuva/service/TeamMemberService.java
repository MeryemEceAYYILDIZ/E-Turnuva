package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.TeamMember;
import com.example.E_Turnuva.entity.User; // User entity'n varsa kullan, yoksa şimdilik String isimle çözelim
import com.example.E_Turnuva.repository.TeamMemberRepository;
import com.example.E_Turnuva.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamMemberService {
    @Autowired private TeamMemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;

    // Takıma yeni üye ekle
    public void addMemberToTeam(Long teamId, String playerName, String role) {
        Team team = teamRepository.findById(teamId).orElseThrow();

        TeamMember member = new TeamMember();
        member.setTeam(team);
        // Burada normalde User tablosundan kullanıcı seçilir ama hızlanmak için
        // TeamMember entitysine geçici bir "playerName" alanı eklediğimizi varsayıyoruz
        // ya da User entity oluşturup bağlıyoruz.
        // Şimdilik TeamMember entitysine "private String playerName;" eklemeni öneririm (User ile uğraşmamak için)
        // Eğer User entity kullanıyorsan burayı ona göre revize ederiz.
        // Hızlı çözüm için User yerine isim tutalım:
        member.setRole(role);

        memberRepository.save(member);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}