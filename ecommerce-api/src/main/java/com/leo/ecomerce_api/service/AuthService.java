package com.leo.ecomerce_api.service;


import com.leo.ecomerce_api.dto.LoginDto;
import com.leo.ecomerce_api.dto.RegisterDto;
import com.leo.ecomerce_api.entity.Usuario;
import com.leo.ecomerce_api.repository.UsuarioRepository;
import com.leo.ecomerce_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtUtil jwtUtil;

    public String registrar (RegisterDto dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRole("USER");
        usuarioRepository.save(usuario);
        return "Usuario registrado com sucesso";

    }

    public String login(LoginDto dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())){
            throw new RuntimeException("senha incorreta");
        }
        return jwtUtil.gerarToken(usuario.getEmail());
    }
}
